import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import GitTree from '../components/GitTree';

describe('GitTree Component', () => {
  const sampleTreeData = [
    { commit: 'abc123', message: 'Initial commit' },
    { commit: 'def456', message: 'Added README' },
    { commit: 'ghi789', message: 'Fixed bug' },
  ];

  const mockOnNodeClick = jest.fn();

  test('renders GitTree with data', () => {
    const treeData = [
      { commit: 'a1b2c3', message: 'Initial commit' },
      { commit: 'd4e5f6', message: 'Added README' },
      { commit: 'g7h8i9', message: 'Implemented feature X' },
    ];

    render(<GitTree treeData={treeData} />);

    expect(screen.getByText('Git Tree')).toBeInTheDocument();
    expect(screen.getByText('a1b2c3')).toBeInTheDocument();
    expect(screen.getByText('Initial commit')).toBeInTheDocument();
    expect(screen.getByText('d4e5f6')).toBeInTheDocument();
    expect(screen.getByText('Added README')).toBeInTheDocument();
    expect(screen.getByText('g7h8i9')).toBeInTheDocument();
    expect(screen.getByText('Implemented feature X')).toBeInTheDocument();
  });

  test('renders empty GitTree', () => {
    render(<GitTree treeData={[]} />);

    expect(screen.getByText('Git Tree')).toBeInTheDocument();
    expect(screen.queryByText('a1b2c3')).not.toBeInTheDocument();
  });

  test('calls onNodeClick when a node is clicked', () => {
    render(<GitTree treeData={sampleTreeData} onNodeClick={mockOnNodeClick} />);

    const firstNode = screen.getByText('abc123');
    fireEvent.click(firstNode);

    expect(mockOnNodeClick).toHaveBeenCalledWith(sampleTreeData[0]);
  });

  test('renders with responsiveness', () => {
    render(<GitTree treeData={sampleTreeData} onNodeClick={mockOnNodeClick} />);

    const container = screen.getByRole('list');
    expect(container).toHaveClass('overflow-x-auto');
  });
});
