import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuestionComponentsPage, QuestionDeleteDialog, QuestionUpdatePage } from './question.page-object';

const expect = chai.expect;

describe('Question e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionComponentsPage: QuestionComponentsPage;
  let questionUpdatePage: QuestionUpdatePage;
  let questionDeleteDialog: QuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Questions', async () => {
    await navBarPage.goToEntity('question');
    questionComponentsPage = new QuestionComponentsPage();
    await browser.wait(ec.visibilityOf(questionComponentsPage.title), 5000);
    expect(await questionComponentsPage.getTitle()).to.eq('kmptDemoApp.question.home.title');
  });

  it('should load create Question page', async () => {
    await questionComponentsPage.clickOnCreateButton();
    questionUpdatePage = new QuestionUpdatePage();
    expect(await questionUpdatePage.getPageTitle()).to.eq('kmptDemoApp.question.home.createOrEditLabel');
    await questionUpdatePage.cancel();
  });

  it('should create and save Questions', async () => {
    const nbButtonsBeforeCreate = await questionComponentsPage.countDeleteButtons();

    await questionComponentsPage.clickOnCreateButton();
    await promise.all([
      questionUpdatePage.setQuestionSetInput('5'),
      questionUpdatePage.setQuestionInput('5'),
      questionUpdatePage.setAnswerInput('answer'),
      questionUpdatePage.setDescriptionInput('description'),
      questionUpdatePage.setTagInput('tag'),
      questionUpdatePage.setScoreInput('5')
    ]);
    expect(await questionUpdatePage.getQuestionSetInput()).to.eq('5', 'Expected questionSet value to be equals to 5');
    expect(await questionUpdatePage.getQuestionInput()).to.eq('5', 'Expected question value to be equals to 5');
    expect(await questionUpdatePage.getAnswerInput()).to.eq('answer', 'Expected Answer value to be equals to answer');
    expect(await questionUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await questionUpdatePage.getTagInput()).to.eq('tag', 'Expected Tag value to be equals to tag');
    expect(await questionUpdatePage.getScoreInput()).to.eq('5', 'Expected score value to be equals to 5');
    await questionUpdatePage.save();
    expect(await questionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await questionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Question', async () => {
    const nbButtonsBeforeDelete = await questionComponentsPage.countDeleteButtons();
    await questionComponentsPage.clickOnLastDeleteButton();

    questionDeleteDialog = new QuestionDeleteDialog();
    expect(await questionDeleteDialog.getDialogTitle()).to.eq('kmptDemoApp.question.delete.question');
    await questionDeleteDialog.clickOnConfirmButton();

    expect(await questionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
