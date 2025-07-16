import puppeteer from 'puppeteer';

describe('End-to-End Test for GitCoach AI', () => {
  let browser;
  let page;

  beforeAll(async () => {
    browser = await puppeteer.launch();
    page = await browser.newPage();
  });

  afterAll(async () => {
    await browser.close();
  });

  test('loads the application and interacts with GitTree', async () => {
    await page.goto('http://localhost:3000');

    // Check if GitTree is rendered
    await page.waitForSelector('h2');
    const gitTreeTitle = await page.$eval('h2', (el) => el.textContent);
    expect(gitTreeTitle).toBe('Git Tree');

    // Simulate clicking a GitTree node
    await page.click('li:nth-child(1)');
    const clickedNodeMessage = await page.$eval('.clicked-node', (el) => el.textContent);
    expect(clickedNodeMessage).toContain('Node clicked: abc123');
  });

  test('submits a commit suggestion', async () => {
    await page.goto('http://localhost:3000');

    // Check if CommitSuggestion is rendered
    await page.waitForSelector('input[placeholder="Enter commit message"]');

    // Simulate entering a commit message and submitting
    await page.type('input[placeholder="Enter commit message"]', 'Initial commit');
    await page.click('button[type="submit"]');

    // Verify submission result
    const submissionMessage = await page.$eval('.submission-result', (el) => el.textContent);
    expect(submissionMessage).toContain('Commit suggestion submitted: Initial commit');
  });
});
