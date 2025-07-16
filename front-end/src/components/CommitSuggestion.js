import React, { useState } from 'react';

const CommitSuggestion = () => {
  const [changes, setChanges] = useState('');
  const [suggestion, setSuggestion] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchSuggestion = async () => {
    setLoading(true);
    try {
      const response = await fetch('/api/commit/suggest', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ changes }),
      });

      if (!response.ok) {
        throw new Error('Failed to fetch suggestion');
      }

      const result = await response.text();
      setSuggestion(result);
    } catch (error) {
      setSuggestion(`Error: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-gray-100 p-4 rounded shadow-md">
      <h2 className="text-xl font-bold mb-4">AI Commit Suggestion</h2>
      <textarea
        className="w-full p-2 border border-gray-300 rounded mb-4"
        placeholder="Describe the changes made..."
        value={changes}
        onChange={(e) => setChanges(e.target.value)}
      ></textarea>
      <button
        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        onClick={fetchSuggestion}
        disabled={loading}
      >
        {loading ? 'Loading...' : 'Get Suggestion'}
      </button>
      {suggestion && (
        <pre className="mt-4 p-2 bg-gray-200 rounded">{suggestion}</pre>
      )}
    </div>
  );
};

export default CommitSuggestion;
