package io.weli.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleSourceDestinationMapper {
    SimpleSourceDestinationMapper INSTANCE = Mappers.getMapper(SimpleSourceDestinationMapper.class);

    SimpleDestination sourceToDestination(SimpleSource source);

    SimpleSource destinationToSource(SimpleDestination destination);
}
