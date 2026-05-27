package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class GenericMapper {

    private GenericMapper() {}

    public static <T, R> List<R> mapToList(List<T> source, Function<T, R> mapper) {
        if (source == null) return null;
        return source.stream().map(mapper).collect(Collectors.toList());
    }
}