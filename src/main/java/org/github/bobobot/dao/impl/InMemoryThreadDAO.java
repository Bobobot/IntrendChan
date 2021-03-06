package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.IThreadDAO;
import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryThreadDAO implements IThreadDAO {
	List<Thread> memory = new ArrayList<>();

	@Override
	public Thread create(Thread thread) {
		thread.setId((long) memory.size());
		memory.add(thread);
		return thread;
	}

	@Override
	public Optional<Thread> update(Thread thread) {
		Optional<Thread> memoryThread = memory.stream()
				.filter(t -> t.getId() == thread.getId())
				.findFirst();

		if (memoryThread.isPresent()) {
			memoryThread.get().setTitle(thread.getTitle());
			memoryThread.get().setBoard(thread.getBoard());
		}

		return memoryThread;
	}

	@Override
	public Optional<Thread> select(int id) {
		Optional<Thread> thread = memory.stream()
				.filter(t -> t.getId() == id)
				.findFirst();
		return thread;
	}

	@Override
	public List<Thread> list() {
		return memory;
	}

	@Override
	public Optional<Thread> delete(int id) {
		Optional<Thread> thread = memory.stream()
				.filter(b -> b.getId() == id)
				.findFirst();
		if (thread.isPresent()) {
			memory.remove(thread.get());
		}

		return thread;
	}
}
