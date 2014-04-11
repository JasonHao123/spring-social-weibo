/*
* Copyright 2011 France Telecom R&D Beijing Co., Ltd 北京法国电信研发中心有限公司
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springframework.social.weibo.api.impl;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.WeiboProfile;

public class FriendTemplateTest extends AbstractWeiboOperationsTest {

	private FriendTemplate friendTemplate;

	@Test
	@Ignore
	public void testGetFriends() {
		String uid = "uid";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/friendships/friends.json?uid="
						+ uid+"&access_token=test"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("friends"), responseHeaders));
		CursoredList<WeiboProfile> friends = friendTemplate.getFriends(uid);
		assertEquals(2, friends.size());
		assertEquals(650, friends.getTotalNumber());
		assertEquals(0, friends.getPreviousCursor());
		assertEquals(1, friends.getNextCursor());
	}

	@Override
	public void setUp() {
		friendTemplate = new FriendTemplate(getObjectMapper(),
				getRestTemplate(), true,"test");
	}

}
