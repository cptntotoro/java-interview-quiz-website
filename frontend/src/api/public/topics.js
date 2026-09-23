export async function getPublicTopics() {
  const response = await fetch('/api/v1/public/topics')

  if (!response.ok) {
    throw new Error(`Failed to load topics: ${response.status}`)
  }

  return response.json()
}
