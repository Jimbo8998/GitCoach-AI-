async function askQuestion(question) {
    const response = await fetch('http://localhost:8080/api/ask', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ question })
    });

    if (!response.ok) {
        throw new Error('Failed to fetch response from API');
    }

    return await response.text();
}

// Example usage
askQuestion('What is AI?')
    .then(response => console.log(response))
    .catch(error => console.error(error));
