import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileTypeInfoComponentsPage, ProfileTypeInfoDeleteDialog, ProfileTypeInfoUpdatePage } from './profile-type-info.page-object';

const expect = chai.expect;

describe('ProfileTypeInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileTypeInfoComponentsPage: ProfileTypeInfoComponentsPage;
  let profileTypeInfoUpdatePage: ProfileTypeInfoUpdatePage;
  let profileTypeInfoDeleteDialog: ProfileTypeInfoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProfileTypeInfos', async () => {
    await navBarPage.goToEntity('profile-type-info');
    profileTypeInfoComponentsPage = new ProfileTypeInfoComponentsPage();
    await browser.wait(ec.visibilityOf(profileTypeInfoComponentsPage.title), 5000);
    expect(await profileTypeInfoComponentsPage.getTitle()).to.eq('kmptDemoApp.profileTypeInfo.home.title');
  });

  it('should load create ProfileTypeInfo page', async () => {
    await profileTypeInfoComponentsPage.clickOnCreateButton();
    profileTypeInfoUpdatePage = new ProfileTypeInfoUpdatePage();
    expect(await profileTypeInfoUpdatePage.getPageTitle()).to.eq('kmptDemoApp.profileTypeInfo.home.createOrEditLabel');
    await profileTypeInfoUpdatePage.cancel();
  });

  it('should create and save ProfileTypeInfos', async () => {
    const nbButtonsBeforeCreate = await profileTypeInfoComponentsPage.countDeleteButtons();

    await profileTypeInfoComponentsPage.clickOnCreateButton();
    await promise.all([
      profileTypeInfoUpdatePage.setChapterInput('chapter'),
      profileTypeInfoUpdatePage.setRankInput('5'),
      profileTypeInfoUpdatePage.setH1Input('h1'),
      profileTypeInfoUpdatePage.setH2Input('h2'),
      profileTypeInfoUpdatePage.setContentInput('content'),
      profileTypeInfoUpdatePage.profileTypeSelectLastOption(),
      profileTypeInfoUpdatePage.profileVariantSelectLastOption()
    ]);
    expect(await profileTypeInfoUpdatePage.getChapterInput()).to.eq('chapter', 'Expected Chapter value to be equals to chapter');
    expect(await profileTypeInfoUpdatePage.getRankInput()).to.eq('5', 'Expected rank value to be equals to 5');
    expect(await profileTypeInfoUpdatePage.getH1Input()).to.eq('h1', 'Expected H1 value to be equals to h1');
    expect(await profileTypeInfoUpdatePage.getH2Input()).to.eq('h2', 'Expected H2 value to be equals to h2');
    expect(await profileTypeInfoUpdatePage.getContentInput()).to.eq('content', 'Expected Content value to be equals to content');
    await profileTypeInfoUpdatePage.save();
    expect(await profileTypeInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileTypeInfoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProfileTypeInfo', async () => {
    const nbButtonsBeforeDelete = await profileTypeInfoComponentsPage.countDeleteButtons();
    await profileTypeInfoComponentsPage.clickOnLastDeleteButton();

    profileTypeInfoDeleteDialog = new ProfileTypeInfoDeleteDialog();
    expect(await profileTypeInfoDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.profileTypeInfo.delete.question');
    await profileTypeInfoDeleteDialog.clickOnConfirmButton();

    expect(await profileTypeInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
