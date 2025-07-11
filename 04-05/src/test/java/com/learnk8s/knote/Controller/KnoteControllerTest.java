package com.learnk8s.knote.Controller;

import com.learnk8s.knote.Controller.KnoteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;
import org.junit.jupiter.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import com.learnk8s.knote.Note.Note;
import com.learnk8s.knote.Repository.NotesRepository;
import com.learnk8s.knote.UploadConfig.KnoteProperties;
import io.micrometer.core.ipc.http.HttpSender.Response;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class KnoteControllerTest {

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

	/*
	 * ROOST_METHOD_HASH=index_224f9d0e4a ROOST_METHOD_SIG_HASH=index_5913f4c0f2
	 *
	 */@Test
	@DisplayName("Validate the response and behavior of index function")
	public void validateUpdatedSourceCodeBehavior() {

		List<Note> mockNotes = List.of(new Note(UUID.randomUUID().toString(), "Updated Description 1"),
				new Note(UUID.randomUUID().toString(), "Updated Description 2"));
		Mockito.when(notesRepository.findAll()).thenReturn(mockNotes);

		ResponseEntity<List<Note>> response = controller.index(model);

		assertNotNull(response, "Response should not be null");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be HTTP OK");
		assertEquals(mockNotes, response.getBody(), "Response body should match the notes from repository");
	}

}