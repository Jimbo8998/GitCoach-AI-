import React, { useState, useEffect } from 'react';

const GuidedChallenges = () => {
  const [challenges, setChallenges] = useState([]);
  const [progress, setProgress] = useState([]);

  useEffect(() => {
    fetch('/api/challenges')
      .then((response) => response.json())
      .then((data) => setChallenges(data))
      .catch((error) => console.error('Error fetching challenges:', error));

    fetch('/api/challenges/progress')
      .then((response) => response.json())
      .then((data) => setProgress(data))
      .catch((error) => console.error('Error fetching progress:', error));
  }, []);

  const updateProgress = async (challenge) => {
    try {
      const response = await fetch('/api/challenges/progress', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ challenge }),
      });

      if (!response.ok) {
        throw new Error('Failed to update progress');
      }

      setProgress((prevProgress) => [...prevProgress, challenge]);
    } catch (error) {
      console.error('Error updating progress:', error);
    }
  };

  return (
    <div className="bg-gray-100 p-4 rounded shadow-md">
      <h2 className="text-xl font-bold mb-4">Guided Challenges</h2>
      <ul className="list-disc pl-5">
        {challenges.map((challenge, index) => (
          <li key={index} className="mb-2">
            <span className="font-medium">{challenge}</span>
            {progress.includes(challenge) ? (
              <span className="ml-2 text-green-500">Completed</span>
            ) : (
              <button
                className="ml-2 bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-600"
                onClick={() => updateProgress(challenge)}
              >
                Mark as Complete
              </button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default GuidedChallenges;
