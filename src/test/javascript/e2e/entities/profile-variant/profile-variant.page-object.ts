import { element, by, ElementFinder } from 'protractor';

export class ProfileVariantComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-profile-variant div table .btn-danger'));
  title = element.all(by.css('jhi-profile-variant div h2#page-heading span')).first();

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

export class ProfileVariantUpdatePage {
  pageTitle = element(by.id('jhi-profile-variant-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  colorInput = element(by.id('field_color'));
  profileTypeSelect = element(by.id('field_profileType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setColorInput(color) {
    await this.colorInput.sendKeys(color);
  }

  async getColorInput() {
    return await this.colorInput.getAttribute('value');
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

export class ProfileVariantDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-profileVariant-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-profileVariant'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
