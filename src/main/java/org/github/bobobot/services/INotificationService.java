package org.github.bobobot.services;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.VoteNotification;

import java.util.List;

public interface INotificationService {

	/**
	 * Creates a new notification.
	 * Does NOT add the notification to the user entity.
	 *
	 * @param notification The notification to be created.
	 * @return The newly created notification.
	 */
	CommentNotification create(CommentNotification notification);

	/**
	 * Creates a comment notification with the arguments provided.
	 * Does NOT add the notification to the user entity.
	 *
	 * @param read            Whether the notification was read or not
	 * @param originalReply   The original reply of the user
	 * @param otherUsersReply The reply of the other user
	 * @return The newly created notification
	 */

	default CommentNotification create(boolean read, Reply originalReply, Reply otherUsersReply) {
		return create(new CommentNotification(read, originalReply, otherUsersReply));
	}

	/**
	 * Creates a vote notification.
	 * Does NOT add the notification to the user entity.
	 *
	 * @param notification The notification to be created
	 * @return The newly created notification.
	 */
	VoteNotification create(VoteNotification notification);

	/**
	 * Creates a vote notification with the arguments provided.
	 * Does NOT add the notification to the user entity.
	 *
	 * @param read          Whether the notification was read or not
	 * @param originalReply The reply that has gotten a vote
	 * @param voteType      The type of the vote (UPVOTE or DOWNVOTE)
	 * @return The newly created notification.
	 */
	default VoteNotification create(boolean read, Reply originalReply, VoteNotification.VoteType voteType) {
		return create(new VoteNotification(read, originalReply, voteType));
	}

	/**
	 * Updates a comment notification.
	 *
	 * @param notification The comment notification to be updated.
	 * @return The updated comment notification.
	 */
	CommentNotification update(CommentNotification notification);

	/**
	 * Updates a comment notification with the arguments provided.
	 *
	 * @param read            Whether the notification was read or not
	 * @param originalReply   The content of the reply that the notification was
	 * @param otherUsersReply
	 * @return The updated notification
	 */
	default CommentNotification update(Long id, boolean read, Reply originalReply, Reply otherUsersReply) {
		return update(new CommentNotification(id, read, originalReply, otherUsersReply));
	}

	/**
	 * Updates a vote notification.
	 *
	 * @param notification The notification to be created
	 * @return The updated notification.
	 */
	VoteNotification update(VoteNotification notification);

	/**
	 * Updates a vote notification with the arguments provided.
	 *
	 * @param read          Whether the notification was read or not
	 * @param originalReply
	 * @param voteType      The type of the vote (UPVOTE or DOWNVOTE)
	 * @return The updated notification.
	 */
	default VoteNotification update(Long id, boolean read, Reply originalReply, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(id, read, originalReply, voteType));
	}

	/**
	 * Finds a comment notification by its ID
	 *
	 * @param id The ID of the comment notification
	 * @return The comment notification if found, otherwise throws an error
	 */
	CommentNotification findCommentNotificationByID(Long id);

	/**
	 * Finds a vote notification by its ID
	 *
	 * @param id The ID of the vote notification
	 * @return The vote notification if found, otherwise throws an error
	 */
	VoteNotification findVoteNotificationByID(Long id);

	List<CommentNotification> getCommentNotificationsByUserId(Long id);

	List<VoteNotification> getVoteNotificationsByUserId(Long id);

	/**
	 * Lists all comment notifications
	 *
	 * @return A list of all comment notifications
	 */
	List<CommentNotification> listCommentNotifications();

	/**
	 * Lists all vote notifications
	 *
	 * @return A list of all vote notifications
	 */
	List<VoteNotification> listVoteNotifications();

	/**
	 * Deletes a comment notification.
	 *
	 * @param id The id of the notification to be deleted.
	 */
	void deleteCommentNotification(Long id);

	/**
	 * Deletes a vote notification.
	 *
	 * @param id The id of the notification to be deleted.
	 */
	void deleteVoteNotification(Long id);

}
