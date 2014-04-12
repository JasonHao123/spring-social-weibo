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

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.support.ParameterMap;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.FriendOperations;
import org.springframework.social.weibo.api.WeiboProfile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

class FriendTemplate extends AbstractWeiboOperations implements
		FriendOperations {

	private String accessToken;

    protected FriendTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized, String accessToken) {
		super(objectMapper, restTemplate, isAuthorized);
		this.accessToken = accessToken;
	}

	@Override
	public CursoredList<WeiboProfile> getFriends(String uid) {
		requireAuthorization();
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.put("uid",Arrays.asList(uid));
		parameters.put("access_token",Arrays.asList(accessToken));
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("friendships/friends.json", parameters),
				JsonNode.class);
		return deserializeCursoredList(dataNode, WeiboProfile.class, "users");
	}
	
	   @Override
	    public CursoredList<WeiboProfile> getFriendsByDisplayName(String uid) {
	        requireAuthorization();
	        OAuth2Parameters parameters = new OAuth2Parameters();
	        parameters.put("screen_name",Arrays.asList(uid));
	        parameters.put("access_token",Arrays.asList(accessToken));
	        parameters.set("count", "2");
	        JsonNode dataNode = restTemplate.getForObject(
	                buildUri("friendships/friends.json", parameters),
	                JsonNode.class);
	        return deserializeCursoredList(dataNode, WeiboProfile.class, "users");
	    }

	@Override
	public CursoredList<WeiboProfile> getFollowers(String uid) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("friendships/followers.json", "uid", uid),
				JsonNode.class);
		return deserializeCursoredList(dataNode, WeiboProfile.class, "users");
	}

    @Override
    public List<WeiboProfile> getFriends(String uid, String cursor) {
        requireAuthorization();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
          parameters.set("uid", uid);
          parameters.set("cursor", cursor);
        //  parameters.set("count", "50");
      JsonNode dataNode = restTemplate.getForObject(
              buildUri("friendships/friends.json", parameters),
              JsonNode.class);
      return deserializeCursoredList(dataNode, WeiboProfile.class, "users");
    }
    
    
    @Override
    public CursoredList<WeiboProfile> getFriendsByDisplayName(String uid, String cursor) {
        requireAuthorization();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
          parameters.set("screen_name", uid);
          parameters.set("cursor", cursor);
          parameters.set("count", "2");
      JsonNode dataNode = restTemplate.getForObject(
              buildUri("friendships/friends.json", parameters),
              JsonNode.class);
      return deserializeCursoredList(dataNode, WeiboProfile.class, "users");
    }
}
