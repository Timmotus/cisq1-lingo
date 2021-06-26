package nl.hu.cisq1.lingo.trainer.data;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.cisq1.lingo.trainer.domain.LingoGame;

public interface SpringGameRepository extends JpaRepository<LingoGame, Long> {
}
