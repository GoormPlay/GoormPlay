package com.goormplay.contentservice.content.tool;

import com.goormplay.contentservice.content.dto.ContentDTO;
import com.goormplay.contentservice.content.entity.Content;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T17:53:54+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Homebrew)"
)
@Component
public class ContentMapperImpl implements ContentMapper {

    @Override
    public Content toEntity(ContentDTO contentDTO) {
        if ( contentDTO == null ) {
            return null;
        }

        Content.ContentBuilder content = Content.builder();

        content.title( contentDTO.getTitle() );
        content.kind( contentDTO.getKind() );
        String[] genre = contentDTO.getGenre();
        if ( genre != null ) {
            content.genre( Arrays.copyOf( genre, genre.length ) );
        }
        content.year( contentDTO.getYear() );
        content.KMRB( contentDTO.getKMRB() );
        String[] cast = contentDTO.getCast();
        if ( cast != null ) {
            content.cast( Arrays.copyOf( cast, cast.length ) );
        }
        String[] director = contentDTO.getDirector();
        if ( director != null ) {
            content.director( Arrays.copyOf( director, director.length ) );
        }
        String[] provider = contentDTO.getProvider();
        if ( provider != null ) {
            content.provider( Arrays.copyOf( provider, provider.length ) );
        }
        content.videoId( contentDTO.getVideoId() );
        content.thumbnail( contentDTO.getThumbnail() );
        content.synopsis( contentDTO.getSynopsis() );
        content.releaseDate( contentDTO.getReleaseDate() );

        content.likesCount( 0 );
        content.commentsCount( 0 );

        return content.build();
    }

    @Override
    public ContentDTO toDTO(Content content) {
        if ( content == null ) {
            return null;
        }

        ContentDTO.ContentDTOBuilder contentDTO = ContentDTO.builder();

        contentDTO.id( content.getId() );
        contentDTO.title( content.getTitle() );
        contentDTO.kind( content.getKind() );
        String[] genre = content.getGenre();
        if ( genre != null ) {
            contentDTO.genre( Arrays.copyOf( genre, genre.length ) );
        }
        contentDTO.year( content.getYear() );
        contentDTO.KMRB( content.getKMRB() );
        String[] cast = content.getCast();
        if ( cast != null ) {
            contentDTO.cast( Arrays.copyOf( cast, cast.length ) );
        }
        String[] provider = content.getProvider();
        if ( provider != null ) {
            contentDTO.provider( Arrays.copyOf( provider, provider.length ) );
        }
        contentDTO.thumbnail( content.getThumbnail() );
        String[] director = content.getDirector();
        if ( director != null ) {
            contentDTO.director( Arrays.copyOf( director, director.length ) );
        }
        contentDTO.videoId( content.getVideoId() );
        contentDTO.releaseDate( content.getReleaseDate() );
        contentDTO.synopsis( content.getSynopsis() );

        return contentDTO.build();
    }
}
