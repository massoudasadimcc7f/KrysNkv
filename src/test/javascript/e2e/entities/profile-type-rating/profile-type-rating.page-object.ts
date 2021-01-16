import { element, by, ElementFinder } from 'protractor';

export class ProfileTypeRatingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-profile-type-rating div table .btn-danger'));
  title = element.all(by.css('jhi-profile-type-rating div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProfileTypeRatingUpdatePage {
  pageTitle = element(by.id('jhi-profile-type-rating-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  characteristicInput = element(by.id('field_characteristic'));
  ratingInput = element(by.id('field_rating'));
  profileTypeSelect = element(by.id('field_profileType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCharacteristicInput(characteristic) {
    await this.characteristicInput.sendKeys(characteristic);
  }

  async getCharacteristicInput() {
    return await this.characteristicInput.getAttribute('value');
  }

  async setRatingInput(rating) {
    await this.ratingInput.sendKeys(rating);
  }

  async getRatingInput() {
    return await this.ratingInput.getAttribute('value');
  }

  async profileTypeSelectLastOption() {
    await this.profileTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async profileTypeSelectOption(option) {
    await this.profileTypeSelect.sendKeys(option);
  }

  getProfileTypeSelect(): ElementFinder {
    return this.profileTypeSelect;
  }

  async getProfileTypeSelectedOption() {
    return await this.profileTypeSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProfileTypeRatingDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-profileTypeRating-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-profileTypeRating'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
