import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileComponentsPage, ProfileDeleteDialog, ProfileUpdatePage } from './profile.page-object';

const expect = chai.expect;

describe('Profile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileComponentsPage: ProfileComponentsPage;
  let profileUpdatePage: ProfileUpdatePage;
  let profileDeleteDialog: ProfileDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Profiles', async () => {
    await navBarPage.goToEntity('profile');
    profileComponentsPage = new ProfileComponentsPage();
    await browser.wait(ec.visibilityOf(profileComponentsPage.title), 5000);
    expect(await profileComponentsPage.getTitle()).to.eq('kmptDemoApp.profile.home.title');
  });

  it('should load create Profile page', async () => {
    await profileComponentsPage.clickOnCreateButton();
    profileUpdatePage = new ProfileUpdatePage();
    expect(await profileUpdatePage.getPageTitle()).to.eq('kmptDemoApp.profile.home.createOrEditLabel');
    await profileUpdatePage.cancel();
  });

  it('should create and save Profiles', async () => {
    const nbButtonsBeforeCreate = await profileComponentsPage.countDeleteButtons();

    await profileComponentsPage.clickOnCreateButton();
    await promise.all([
      profileUpdatePage.setNameInput('name'),
      profileUpdatePage.setJobTitleInput('jobTitle'),
      profileUpdatePage.setNotesInput('notes'),
      profileUpdatePage.setRelationInput('relation'),
      profileUpdatePage.profileVariantSelectLastOption(),
      profileUpdatePage.userSelectLastOption()
      // profileUpdatePage.questionSelectLastOption(),
    ]);
    expect(await profileUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await profileUpdatePage.getJobTitleInput()).to.eq('jobTitle', 'Expected JobTitle value to be equals to jobTitle');
    expect(await profileUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    const selectedMe = profileUpdatePage.getMeInput();
    if (await selectedMe.isSelected()) {
      await profileUpdatePage.getMeInput().click();
      expect(await profileUpdatePage.getMeInput().isSelected(), 'Expected me not to be selected').to.be.false;
    } else {
      await profileUpdatePage.getMeInput().click();
      expect(await profileUpdatePage.getMeInput().isSelected(), 'Expected me to be selected').to.be.true;
    }
    expect(await profileUpdatePage.getRelationInput()).to.eq('relation', 'Expected Relation value to be equals to relation');
    await profileUpdatePage.save();
    expect(await profileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Profile', async () => {
    const nbButtonsBeforeDelete = await profileComponentsPage.countDeleteButtons();
    await profileComponentsPage.clickOnLastDeleteButton();

    profileDeleteDialog = new ProfileDeleteDialog();
    expect(await profileDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.profile.delete.question');
    await profileDeleteDialog.clickOnConfirmButton();

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
