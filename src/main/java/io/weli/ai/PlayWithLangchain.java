package io.weli.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingMatch;

import java.util.List;

// https://www.mastertheboss.com/various-stuff/ai/using-rag-with-langchain4j-and-ollama3/
@SuppressWarnings({"deprecation", "unchecked"})
public class PlayWithLangchain {
    public static void main(String[] args) {
        // Create an in-memory embedding store
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Create a document splitter
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);

        // Create an ingestor
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingStore(embeddingStore)
                .build();

        // Create a document
        Document document = Document.from("Hello, world!");

        // Ingest the document
        ingestor.ingest(document);

        // Create a query embedding (using a simple vector for demonstration)
        float[] queryVector = new float[384]; // Typical embedding dimension
        for (int i = 0; i < queryVector.length; i++) {
            queryVector[i] = (float) Math.random();
        }
        Embedding queryEmbedding = new Embedding(queryVector);

        // Find relevant segments
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);

        // Print the results
        for (EmbeddingMatch<TextSegment> match : relevant) {
            System.out.println("Score: " + match.score());
            System.out.println("Text: " + match.embedded().text());
        }
    }
}
