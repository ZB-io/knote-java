package com.learnk8s.knote.Controller;

import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api;
import com.learnk8s.knote.Note.Note;
import com.learnk8s.knote.Repository.NotesRepository;
import com.learnk8s.knote.UploadConfig.KnoteProperties;
import java.util.UUID;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.multipart.MultipartFile;
import io.micrometer.core.ipc.http.HttpSender.Response;
import java.io.File;
import com.learnk8s.knote.Controller.KnoteController;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions.assertEquals;

public class KnoteControllerTest {

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
@Tag("valid")
public void handleEmptyNotesList() {

    when(notesRepository.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Note>> response = controller.index(model);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("valid")
	public void handleNonEmptyNotesList() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description 1"),
				new Note(UUID.randomUUID().toString(), "Mock Description 2"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(model);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockNotes, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
@Tag("valid")
public void validateHttpResponseCode() {

    when(notesRepository.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Note>> emptyResponse = controller.index(model);

    List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description"));
    when(notesRepository.findAll()).thenReturn(mockNotes);

    ResponseEntity<List<Note>> nonEmptyResponse = controller.index(model);

    assertEquals(HttpStatus.OK, emptyResponse.getStatusCode());
    assertEquals(HttpStatus.OK, nonEmptyResponse.getStatusCode());
}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("boundary")
	public void handleNullModelObject() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(null);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockNotes, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
@Tag("boundary")
public void handleGetAllNotesException() {

    when(notesRepository.findAll()).thenThrow(new RuntimeException("Mocked Exception"));

    assertThrows(RuntimeException.class, () -> controller.index(model));
}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
@Tag("invalid")
public void handleEmptyRepositoryData() {

    when(notesRepository.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Note>> response = controller.index(model);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("valid")
	public void validateResponseContentsForMultipleNotes() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description 1"),
				new Note(UUID.randomUUID().toString(), "Mock Description 2"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(model);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockNotes, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("integration")
	public void validateIndirectRepositoryAccess() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(model);

		assertNotNull(response);

		verify(notesRepository, times(1)).findAll();
	}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("boundary")
	public void handleConcurrentAccess() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		Runnable task = () -> {
			ResponseEntity<List<Note>> response = controller.index(model);
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(mockNotes, response.getBody());
		};

		Thread thread1 = new Thread(task);
		Thread thread2 = new Thread(task);
		thread1.start();
		thread2.start();
	}

	/*
	 * ROOST_METHOD_HASH=index_544d09df63 ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@Tag("valid")
	public void validateModelPopulation() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Mock Description 1"),
				new Note(UUID.randomUUID().toString(), "Mock Description 2"));
		when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(model);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockNotes, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@Test
	@Tag("invalid")
	public void handleNullUploadAndPublish() throws Exception {

		String upload = null;
		String publish = null;
		String description = "Sample description";

		ResponseEntity<?> response = knoteController.saveNotes(file, description, publish, upload, model);

		assertEquals(HttpStatus.BAD_REQUEST, ((HttpStatusCode) response.getBody()).toString());
	}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@Test
@Tag("invalid")
public void handleInvalidUploadWithoutFile() throws Exception {

    when(file.getOriginalFilename()).thenReturn(null);
    String upload = "Upload";
    String publish = null;
    String description = "Sample description";

    ResponseEntity<?> response = knoteController.saveNotes(file, description, publish, upload, model);

    assertEquals(HttpStatus.BAD_REQUEST, ((HttpStatusCode) response.getBody()).toString());
}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@Test
@Tag("valid")
public void handleSuccessUploadWithValidFile() throws Exception {


    when(file.getOriginalFilename()).thenReturn("testFile.jpg");
    String upload = "Upload";
    String publish = null;
    String description = "Sample description";

    ResponseEntity<?> response = knoteController.saveNotes(file, description, publish, upload, model);

    assertEquals(HttpStatus.CREATED, ((HttpStatusCode) response.getBody()).toString());
}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@Test
@Tag("valid")
public void handleSuccessPublish() throws Exception {


    when(file.getOriginalFilename()).thenReturn(null);
    String upload = null;
    String publish = "Publish";
    String description = "Sample description";

    ResponseEntity<?> response = knoteController.saveNotes(file, description, publish, upload, model);

    assertEquals(HttpStatus.CREATED, ((HttpStatusCode) response.getBody()).toString());
}

	/*
	 * ROOST_METHOD_HASH=saveNotes_a7f7d80b71 ROOST_METHOD_SIG_HASH=saveNotes_584e2e5550
	 *
	 */@Test
@Tag("boundary")
public void handleBoundaryCaseEmptyFileName() throws Exception {

    when(file.getOriginalFilename()).thenReturn("");
    String upload = "Upload";
    String publish = null;
    String description = "Sample description";

    ResponseEntity<?> response = knoteController.saveNotes(file, description, publish, upload, model);

    assertEquals(HttpStatus.BAD_REQUEST, ((HttpStatusCode) response.getBody()).toString());
}

}