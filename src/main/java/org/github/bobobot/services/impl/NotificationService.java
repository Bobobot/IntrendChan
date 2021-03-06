package org.github.bobobot.services.impl;

import org.github.bobobot.entities.*;
import org.github.bobobot.repositories.ICommentNotificationRepository;
import org.github.bobobot.repositories.IUserRepository;
import org.github.bobobot.repositories.IVoteNotificationRepository;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class NotificationService implements INotificationService {

	IUserService userService;
	@Autowired
	private ICommentNotificationRepository commentRepository;
	@Autowired
	private IVoteNotificationRepository voteRepository;
	@Autowired
	private IUserRepository userRepository;

	public NotificationService(IUserService userService) {
		this.userService = userService;
	}

	private Notification getNotificationIfPresent(Optional<? extends Notification> notification) {
		if (!notification.isPresent()) {
			throw new IllegalArgumentException("Notification was not found!");
		}
		return notification.get();
	}

	private void checkIfRepliesInTheSameThread(Reply originalReply, Reply newReply) {
		if (!originalReply.getThread().getId().equals(newReply.getThread().getId())) {
			throw new IllegalArgumentException("Replies not in the same thread!");
		}
	}

	@Override
	@Transactional
	public CommentNotification create(CommentNotification notification) {
		checkIfRepliesInTheSameThread(notification.getOriginalReply(), notification.getOtherUsersReply());
		notification = commentRepository.save(notification);
		User user = notification.getUser();
		userService.addCommentNotification(user, notification);
		return notification;
	}

	@Override
	public VoteNotification create(VoteNotification notification) {
		User user = notification.getUser();
		userService.addVoteNotification(user, notification);
		return notification;
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		checkIfRepliesInTheSameThread(tempNotification.getOriginalReply(), tempNotification.getOtherUsersReply());
		getNotificationIfPresent(commentRepository.findById(tempNotification.getId())); //throw an error if it doesn't exist
		return commentRepository.save(tempNotification);
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		getNotificationIfPresent(voteRepository.findById(tempNotification.getId())); //throw an error if it doesn't exist
		return voteRepository.save(tempNotification);
	}

	@Override
	public CommentNotification findCommentNotificationByID(Long id) {
		Optional<CommentNotification> notification = commentRepository.findById(id);
		return (CommentNotification) getNotificationIfPresent(notification);
	}

	@Override
	public VoteNotification findVoteNotificationByID(Long id) {
		Optional<VoteNotification> notification = voteRepository.findById(id);
		return (VoteNotification) getNotificationIfPresent(notification);
	}

	@Override
	public List<CommentNotification> getCommentNotificationsByUserId(Long id) {
		return commentRepository.getByUserId(id);
	}

	@Override
	public List<VoteNotification> getVoteNotificationsByUserId(Long id) {
		return voteRepository.getByUserId(id);
	}

	@Override
	public List<CommentNotification> listCommentNotifications() {
		return commentRepository.findAll();
	}

	@Override
	public List<VoteNotification> listVoteNotifications() {
		return voteRepository.findAll();
	}

	@Override
	public void deleteCommentNotification(Long id) {
		getNotificationIfPresent(commentRepository.findById(id));
		commentRepository.deleteById(id);
	}

	@Override
	public void deleteVoteNotification(Long id) {
		getNotificationIfPresent(voteRepository.findById(id));
		voteRepository.deleteById(id);
	}
}
