import React, { useState } from 'react';
import GitTree from './components/GitTree';
import CommitSuggestion from './components/CommitSuggestion';
import GuidedChallenges from './components/GuidedChallenges';

const App = () => {
  const [treeData, setTreeData] = useState([
    { commit: 'a1b2c3', message: 'Initial commit' },
    { commit: 'd4e5f6', message: 'Added README' },
    { commit: 'g7h8i9', message: 'Implemented feature X' },
  ]);

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-6">GitCoach AI</h1>
      <GitTree treeData={treeData} />
      <CommitSuggestion />
      <GuidedChallenges />
    </div>
  );
};

export default App;
