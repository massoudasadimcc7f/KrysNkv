import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileTypeComponentsPage, ProfileTypeDeleteDialog, ProfileTypeUpdatePage } from './profile-type.page-object';

const expect = chai.expect;

describe('ProfileType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileTypeComponentsPage: ProfileTypeComponentsPage;
  let profileTypeUpdatePage: ProfileTypeUpdatePage;
  let profileTypeDeleteDialog: ProfileTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProfileTypes', async () => {
    await navBarPage.goToEntity('profile-type');
    profileTypeComponentsPage = new ProfileTypeComponentsPage();
    await browser.wait(ec.visibilityOf(profileTypeComponentsPage.title), 5000);
    expect(await profileTypeComponentsPage.getTitle()).to.eq('kmptDemoApp.profileType.home.title');
  });

  it('should load create ProfileType page', async () => {
    await profileTypeComponentsPage.clickOnCreateButton();
    profileTypeUpdatePage = new ProfileTypeUpdatePage();
    expect(await profileTypeUpdatePage.getPageTitle()).to.eq('kmptDemoApp.profileType.home.createOrEditLabel');
    await profileTypeUpdatePage.cancel();
  });

  it('should create and save ProfileTypes', async () => {
    const nbButtonsBeforeCreate = await profileTypeComponentsPage.countDeleteButtons();

    await profileTypeComponentsPage.clickOnCreateButton();
    await promise.all([profileTypeUpdatePage.setNameInput('name'), profileTypeUpdatePage.setDescriptionInput('description')]);
    expect(await profileTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await profileTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await profileTypeUpdatePage.save();
    expect(await profileTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProfileType', async () => {
    const nbButtonsBeforeDelete = await profileTypeComponentsPage.countDeleteButtons();
    await profileTypeComponentsPage.clickOnLastDeleteButton();

    profileTypeDeleteDialog = new ProfileTypeDeleteDialog();
    expect(await profileTypeDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.profileType.delete.question');
    await profileTypeDeleteDialog.clickOnConfirmButton();

    expect(await profileTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
