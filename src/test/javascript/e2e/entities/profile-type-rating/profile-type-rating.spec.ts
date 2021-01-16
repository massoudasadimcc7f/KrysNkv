import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProfileTypeRatingComponentsPage,
  ProfileTypeRatingDeleteDialog,
  ProfileTypeRatingUpdatePage
} from './profile-type-rating.page-object';

const expect = chai.expect;

describe('ProfileTypeRating e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileTypeRatingComponentsPage: ProfileTypeRatingComponentsPage;
  let profileTypeRatingUpdatePage: ProfileTypeRatingUpdatePage;
  let profileTypeRatingDeleteDialog: ProfileTypeRatingDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProfileTypeRatings', async () => {
    await navBarPage.goToEntity('profile-type-rating');
    profileTypeRatingComponentsPage = new ProfileTypeRatingComponentsPage();
    await browser.wait(ec.visibilityOf(profileTypeRatingComponentsPage.title), 5000);
    expect(await profileTypeRatingComponentsPage.getTitle()).to.eq('kmptDemoApp.profileTypeRating.home.title');
  });

  it('should load create ProfileTypeRating page', async () => {
    await profileTypeRatingComponentsPage.clickOnCreateButton();
    profileTypeRatingUpdatePage = new ProfileTypeRatingUpdatePage();
    expect(await profileTypeRatingUpdatePage.getPageTitle()).to.eq('kmptDemoApp.profileTypeRating.home.createOrEditLabel');
    await profileTypeRatingUpdatePage.cancel();
  });

  it('should create and save ProfileTypeRatings', async () => {
    const nbButtonsBeforeCreate = await profileTypeRatingComponentsPage.countDeleteButtons();

    await profileTypeRatingComponentsPage.clickOnCreateButton();
    await promise.all([
      profileTypeRatingUpdatePage.setCharacteristicInput('characteristic'),
      profileTypeRatingUpdatePage.setRatingInput('5'),
      profileTypeRatingUpdatePage.profileTypeSelectLastOption()
    ]);
    expect(await profileTypeRatingUpdatePage.getCharacteristicInput()).to.eq(
      'characteristic',
      'Expected Characteristic value to be equals to characteristic'
    );
    expect(await profileTypeRatingUpdatePage.getRatingInput()).to.eq('5', 'Expected rating value to be equals to 5');
    await profileTypeRatingUpdatePage.save();
    expect(await profileTypeRatingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileTypeRatingComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProfileTypeRating', async () => {
    const nbButtonsBeforeDelete = await profileTypeRatingComponentsPage.countDeleteButtons();
    await profileTypeRatingComponentsPage.clickOnLastDeleteButton();

    profileTypeRatingDeleteDialog = new ProfileTypeRatingDeleteDialog();
    expect(await profileTypeRatingDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.profileTypeRating.delete.question');
    await profileTypeRatingDeleteDialog.clickOnConfirmButton();

    expect(await profileTypeRatingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
