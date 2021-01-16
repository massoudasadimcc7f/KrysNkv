import { element, by, ElementFinder } from 'protractor';

export class QuestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question div table .btn-danger'));
  title = element.all(by.css('jhi-question div h2#page-heading span')).first();

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

export class QuestionUpdatePage {
  pageTitle = element(by.id('jhi-question-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  questionSetInput = element(by.id('field_questionSet'));
  questionInput = element(by.id('field_question'));
  answerInput = element(by.id('field_answer'));
  descriptionInput = element(by.id('field_description'));
  tagInput = element(by.id('field_tag'));
  scoreInput = element(by.id('field_score'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuestionSetInput(questionSet) {
    await this.questionSetInput.sendKeys(questionSet);
  }

  async getQuestionSetInput() {
    return await this.questionSetInput.getAttribute('value');
  }

  async setQuestionInput(question) {
    await this.questionInput.sendKeys(question);
  }

  async getQuestionInput() {
    return await this.questionInput.getAttribute('value');
  }

  async setAnswerInput(answer) {
    await this.answerInput.sendKeys(answer);
  }

  async getAnswerInput() {
    return await this.answerInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setTagInput(tag) {
    await this.tagInput.sendKeys(tag);
  }

  async getTagInput() {
    return await this.tagInput.getAttribute('value');
  }

  async setScoreInput(score) {
    await this.scoreInput.sendKeys(score);
  }

  async getScoreInput() {
    return await this.scoreInput.getAttribute('value');
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

export class QuestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-question-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-question'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
