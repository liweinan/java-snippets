package io.weli.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaLanguageModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

//import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

// https://www.mastertheboss.com/various-stuff/ai/using-rag-with-langchain4j-and-ollama3/
public class PlayWithLangchain {
    public static void main(String[] args) throws Exception {
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("deepseek-r1")
                .build();
        EmbeddingStore embeddingStore = new InMemoryEmbeddingStore();
        URL fileUrl = PlayWithLangchain.class.getResource("/dictionary.txt");
        Path path = Paths.get(fileUrl.toURI());
        Document document = FileSystemDocumentLoader.loadDocument(path, new TextDocumentParser());
        DocumentSplitter splitter = DocumentSplitters.recursive(600, 0);
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);
        Embedding queryEmbedding = embeddingModel.embed("What is the Shadowmire ?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        String information = embeddingMatch.embedded().text();

        Prompt prompt = PromptTemplate.from("""
                Tell me about {{name}}?

                Use the following information to answer the question:
                {{information}}
                """).apply(Map.of("name", "Shadowmire","information", information));
        // Initialize the language model for generating the response
        OllamaLanguageModel model = OllamaLanguageModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("deepseek-r1")
                .timeout(Duration.of(100, ChronoUnit.SECONDS))
                .build();

        String answer = model.generate(prompt).content();
        System.out.println("Answer:"+answer);
    }
}
