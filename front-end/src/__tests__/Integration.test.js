import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import App from '../App';

describe('Integration Test for GitCoach AI', () => {
  test('renders GitTree and interacts with nodes', async () => {
    render(<App />);

    // Check if GitTree is rendered
    expect(screen.getByText('Git Tree')).toBeInTheDocument();

    // Simulate interaction with a GitTree node
    const firstNode = await screen.findByText('abc123');
    fireEvent.click(firstNode);

    // Verify interaction result
    expect(screen.getByText('Node clicked: abc123')).toBeInTheDocument();
  });

  test('renders CommitSuggestion and submits a suggestion', async () => {
    render(<App />);

    // Check if CommitSuggestion is rendered
    expect(screen.getByPlaceholderText('Enter commit message')).toBeInTheDocument();

    // Simulate entering a commit message and submitting
    const input = screen.getByPlaceholderText('Enter commit message');
    fireEvent.change(input, { target: { value: 'Initial commit' } });
    const submitButton = screen.getByText('Submit');
    fireEvent.click(submitButton);

    // Verify submission result
    expect(screen.getByText('Commit suggestion submitted: Initial commit')).toBeInTheDocument();
  });
});
