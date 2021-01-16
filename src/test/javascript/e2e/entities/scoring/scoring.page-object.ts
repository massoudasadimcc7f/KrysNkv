import { element, by, ElementFinder } from 'protractor';

export class ScoringComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-scoring div table .btn-danger'));
  title = element.all(by.css('jhi-scoring div h2#page-heading span')).first();

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

export class ScoringUpdatePage {
  pageTitle = element(by.id('jhi-scoring-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  score1Input = element(by.id('field_score1'));
  score2Input = element(by.id('field_score2'));
  profileVariantSelect = element(by.id('field_profileVariant'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setScore1Input(score1) {
    await this.score1Input.sendKeys(score1);
  }

  async getScore1Input() {
    return await this.score1Input.getAttribute('value');
  }

  async setScore2Input(score2) {
    await this.score2Input.sendKeys(score2);
  }

  async getScore2Input() {
    return await this.score2Input.getAttribute('value');
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

export class ScoringDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-scoring-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-scoring'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
