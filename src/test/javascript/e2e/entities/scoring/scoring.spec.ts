import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScoringComponentsPage, ScoringDeleteDialog, ScoringUpdatePage } from './scoring.page-object';

const expect = chai.expect;

describe('Scoring e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scoringComponentsPage: ScoringComponentsPage;
  let scoringUpdatePage: ScoringUpdatePage;
  let scoringDeleteDialog: ScoringDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scorings', async () => {
    await navBarPage.goToEntity('scoring');
    scoringComponentsPage = new ScoringComponentsPage();
    await browser.wait(ec.visibilityOf(scoringComponentsPage.title), 5000);
    expect(await scoringComponentsPage.getTitle()).to.eq('kmptDemoApp.scoring.home.title');
  });

  it('should load create Scoring page', async () => {
    await scoringComponentsPage.clickOnCreateButton();
    scoringUpdatePage = new ScoringUpdatePage();
    expect(await scoringUpdatePage.getPageTitle()).to.eq('kmptDemoApp.scoring.home.createOrEditLabel');
    await scoringUpdatePage.cancel();
  });

  it('should create and save Scorings', async () => {
    const nbButtonsBeforeCreate = await scoringComponentsPage.countDeleteButtons();

    await scoringComponentsPage.clickOnCreateButton();
    await promise.all([
      scoringUpdatePage.setScore1Input('5'),
      scoringUpdatePage.setScore2Input('5'),
      scoringUpdatePage.profileVariantSelectLastOption()
    ]);
    expect(await scoringUpdatePage.getScore1Input()).to.eq('5', 'Expected score1 value to be equals to 5');
    expect(await scoringUpdatePage.getScore2Input()).to.eq('5', 'Expected score2 value to be equals to 5');
    await scoringUpdatePage.save();
    expect(await scoringUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await scoringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Scoring', async () => {
    const nbButtonsBeforeDelete = await scoringComponentsPage.countDeleteButtons();
    await scoringComponentsPage.clickOnLastDeleteButton();

    scoringDeleteDialog = new ScoringDeleteDialog();
    expect(await scoringDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.scoring.delete.question');
    await scoringDeleteDialog.clickOnConfirmButton();

    expect(await scoringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
