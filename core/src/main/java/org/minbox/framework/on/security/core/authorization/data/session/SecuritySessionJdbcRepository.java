/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.core.authorization.data.session;

import org.minbox.framework.on.security.core.authorization.jdbc.OnSecurityBaseJdbcRepositorySupport;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityColumnName;
import org.minbox.framework.on.security.core.authorization.jdbc.definition.OnSecurityTables;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.Condition;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.ConditionGroup;
import org.minbox.framework.on.security.core.authorization.jdbc.sql.operator.SqlLogicalOperator;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 会话数据存储库JDBC实现类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecuritySessionJdbcRepository extends OnSecurityBaseJdbcRepositorySupport<SecuritySession, String>
        implements SecuritySessionRepository {
    public SecuritySessionJdbcRepository(JdbcOperations jdbcOperations) {
        super(OnSecurityTables.SecuritySession, jdbcOperations);
    }

    @Override
    public void save(SecuritySession session) {
        Assert.notNull(session, "session cannot be null");
        SecuritySession securitySession = this.selectOne(session.getId());
        if (securitySession == null) {
            this.insert(session);
        } else {
            this.update(session);
        }
    }

    @Override
    public SecuritySession findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            return this.findUnknownToken(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return this.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return this.findByAuthorizationCode(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return this.findByAccessToken(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return this.findByRefreshToken(token);
        }
        return null;
    }

    private SecuritySession findUnknownToken(String token) {
        List<SecuritySession> securitySessionList = this.select(
                ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumnName.State, token).build()).build(),
                ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumnName.AuthorizationCodeValue, token).build()).operator(SqlLogicalOperator.OR).build(),
                ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumnName.AccessTokenValue, token).build()).operator(SqlLogicalOperator.OR).build(),
                ConditionGroup.withCondition(Condition.withColumn(OnSecurityColumnName.RefreshTokenValue, token).build()).operator(SqlLogicalOperator.OR).build()
        );
        return !ObjectUtils.isEmpty(securitySessionList) ? securitySessionList.get(0) : null;
    }

    private SecuritySession findByState(String state) {
        Condition stateCondition = Condition.withColumn(OnSecurityColumnName.State, state).build();
        return this.selectOne(stateCondition);
    }

    private SecuritySession findByAuthorizationCode(String authorizationCode) {
        Condition authorizationCodeCondition = Condition.withColumn(OnSecurityColumnName.AuthorizationCodeValue, authorizationCode).build();
        return this.selectOne(authorizationCodeCondition);
    }

    private SecuritySession findByAccessToken(String accessToken) {
        Condition accessTokenCondition = Condition.withColumn(OnSecurityColumnName.AccessTokenValue, accessToken).build();
        return this.selectOne(accessTokenCondition);
    }

    private SecuritySession findByRefreshToken(String refreshToken) {
        Condition refreshTokenCondition = Condition.withColumn(OnSecurityColumnName.RefreshTokenValue, refreshToken).build();
        return this.selectOne(refreshTokenCondition);
    }
}
