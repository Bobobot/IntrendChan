package org.github.bobobot.services;

import org.github.bobobot.entities.Board;

import java.util.List;

public interface IBoardService {

	/**
	 * Creates a board
	 *
	 * @param board The board to be created.
	 * @return The created board.
	 */
	Board create(Board board);

	/**
	 * Creates a board
	 *
	 * @param shortName The short name of the board.
	 * @param longName  The longer description of the board - should only be a couple words long.
	 * @return The created board.
	 */
	default Board create(String shortName, String longName) {
		return create(new Board(shortName, longName));
	}

	/**
	 * Updates a board
	 *
	 * @param board The board to be updated.
	 * @return The updated board.
	 */
	Board update(Board board);


	/**
	 * Updates a board
	 *
	 * @param id        the ID of the board to be updated
	 * @param shortName The short name of the board.
	 * @param longName  The longer description of the board - should only be a couple words long.
	 * @return The updated board.
	 */
	default Board update(Long id, String shortName, String longName) {
		return update(new Board(id, shortName, longName));
	}

	/**
	 * Lists all existing boards.
	 *
	 * @return list of all existing boards.
	 */
	List<Board> list();

	/**
	 * Finds a board by its ID.
	 *
	 * @param id The ID of the board to be found.
	 * @return The found board wrapped in an optional.
	 */
	Board findById(Long id);

	/**
	 * Finds a board by its short name.
	 *
	 * @param shortName The short name of the board to be found.
	 * @return The found board wrapped in an optional.
	 */
	Board findByShortName(String shortName);

	/**
	 * Deletes a board.
	 *
	 * @param id The ID of the board to be deleted.
	 */
	void delete(Long id);
}
