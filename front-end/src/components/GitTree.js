import React from 'react';

const GitTree = ({ treeData, onNodeClick }) => {
  return (
    <div className="bg-gray-100 p-4 rounded shadow-md max-w-full overflow-x-auto">
      <h2 className="text-xl font-bold mb-4">Git Tree</h2>
      <ul className="list-disc pl-5">
        {treeData.map((node, index) => (
          <li
            key={index}
            className="mb-2 cursor-pointer hover:bg-gray-200 p-2 rounded"
            onClick={() => onNodeClick(node)}
          >
            <span className="font-medium text-blue-600">{node.commit}</span> - {node.message}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default GitTree;
