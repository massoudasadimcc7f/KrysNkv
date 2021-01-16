import { element, by, ElementFinder } from 'protractor';

export class ProfileTypeInfoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-profile-type-info div table .btn-danger'));
  title = element.all(by.css('jhi-profile-type-info div h2#page-heading span')).first();

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

export class ProfileTypeInfoUpdatePage {
  pageTitle = element(by.id('jhi-profile-type-info-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  chapterInput = element(by.id('field_chapter'));
  rankInput = element(by.id('field_rank'));
  h1Input = element(by.id('field_h1'));
  h2Input = element(by.id('field_h2'));
  contentInput = element(by.id('field_content'));
  profileTypeSelect = element(by.id('field_profileType'));
  profileVariantSelect = element(by.id('field_profileVariant'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setChapterInput(chapter) {
    await this.chapterInput.sendKeys(chapter);
  }

  async getChapterInput() {
    return await this.chapterInput.getAttribute('value');
  }

  async setRankInput(rank) {
    await this.rankInput.sendKeys(rank);
  }

  async getRankInput() {
    return await this.rankInput.getAttribute('value');
  }

  async setH1Input(h1) {
    await this.h1Input.sendKeys(h1);
  }

  async getH1Input() {
    return await this.h1Input.getAttribute('value');
  }

  async setH2Input(h2) {
    await this.h2Input.sendKeys(h2);
  }

  async getH2Input() {
    return await this.h2Input.getAttribute('value');
  }

  async setContentInput(content) {
    await this.contentInput.sendKeys(content);
  }

  async getContentInput() {
    return await this.contentInput.getAttribute('value');
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

  async profileVariantSelectLastOption() {
    await this.profileVariantSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async profileVariantSelectOption(option) {
    await this.profileVariantSelect.sendKeys(option);
  }

  getProfileVariantSelect(): ElementFinder {
    return this.profileVariantSelect;
  }

  async getProfileVariantSelectedOption() {
    return await this.profileVariantSelect.element(by.css('option:checked')).getText();
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

export class ProfileTypeInfoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-profileTypeInfo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-profileTypeInfo'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
