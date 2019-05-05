package com.lostforstupid.movietracker.poster;

import com.mongodb.client.gridfs.model.GridFSFile;
import java.io.IOException;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("FieldCanBeLocal")
@AllArgsConstructor
@Service
public class PosterService {

  private final GridFsTemplate gridFsTemplate;

  private final String FILENAME_CRITERIA = "filename";

  public void savePoster(MultipartFile poster, String name) throws IOException {
    gridFsTemplate.store(poster.getInputStream(), name, poster.getContentType());
  }

  public byte[] getPoster(String name) {

    GridFSFile posterFile = getPosterGridFSDBFile(name);

    byte[] poster;

    try {
      InputStream inputStream = getPosterInputStream(posterFile);
      poster = IOUtils.toByteArray(inputStream);
    } catch (IOException e) {
      poster = null;
    }

    return poster;
  }

  private GridFSFile getPosterGridFSDBFile(String name) {
    return gridFsTemplate.findOne(new Query(Criteria.where(FILENAME_CRITERIA).is(name)));
  }

  private InputStream getPosterInputStream(GridFSFile file) throws IOException {

    GridFsResource gridFsResource = gridFsTemplate.getResource(file);
    return gridFsResource.getInputStream();
  }
}
