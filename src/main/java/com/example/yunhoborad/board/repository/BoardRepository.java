package com.example.yunhoborad.board.repository;

import com.example.yunhoborad.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface BoardRepository extends Repository<Board, Long> {

    Board save(Board board);

    Optional<Board> findById(Long id);

    List<Board> findAll();
}
