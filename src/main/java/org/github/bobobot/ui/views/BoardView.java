package org.github.bobobot.ui.views;


import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.ui.views.layouts.ReplyLayout;
import org.github.bobobot.ui.views.layouts.ThreadLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SpringView(name = BoardView.name)
@SpringComponent
@Slf4j
public class BoardView extends VerticalLayout implements View {
	public static final String name = "boardView";

	//TODO: ezt nem így kéne
	@Autowired
	IBoardService boardService;

	Board board;

	private void setViewBoard(ViewChangeEvent event) {
		Long boardId = -1L;
		try {
			boardId = Long.parseLong(event.getParameters());
		} catch (NumberFormatException e) {
			log.error("Incorrect board parameter: " + event.getParameters());
			e.printStackTrace();
		}

		board = boardService.findById(boardId);
	}

	@Override
	@Transactional
	public void enter(ViewChangeEvent event) {
		log.info("Entered board view");
		setViewBoard(event);

		//TODO: posztolás

		addStyleName("row");

		for (Thread thread : board.getThreads()) {
			ThreadLayout threadLayout = new ThreadLayout(thread);
			addComponent(threadLayout);
		}
	}
}
