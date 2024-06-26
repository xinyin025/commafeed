package com.commafeed.backend.cache;

import java.util.List;
import java.util.Set;

import com.commafeed.backend.Digests;
import com.commafeed.backend.feed.parser.FeedParserResult.Entry;
import com.commafeed.backend.model.Feed;
import com.commafeed.backend.model.FeedSubscription;
import com.commafeed.backend.model.User;
import com.commafeed.frontend.model.Category;
import com.commafeed.frontend.model.UnreadCount;

public abstract class CacheService {

	// feed entries for faster refresh
	public abstract Set<String> getLastEntries(Feed feed);

	public abstract void setLastEntries(Feed feed, List<String> entries);

	public String buildUniqueEntryKey(Entry entry) {
		return Digests.sha1Hex(entry.guid() + entry.url());
	}

	// user categories
	public abstract Category getUserRootCategory(User user);

	public abstract void setUserRootCategory(User user, Category category);

	public abstract void invalidateUserRootCategory(User... users);

	// unread count
	public abstract UnreadCount getUnreadCount(FeedSubscription sub);

	public abstract void setUnreadCount(FeedSubscription sub, UnreadCount count);

	public abstract void invalidateUnreadCount(FeedSubscription... subs);

}
