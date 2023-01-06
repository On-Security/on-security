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

package org.minbox.framework.on.security.core.authorization.jackson2;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.key.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * Jackson的时间转换模块配置
 *
 * @author 恒宇少年
 * @since 0.0.5
 */
public class OnSecurityTimeModule extends SimpleModule {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    public OnSecurityTimeModule() {
        super(PackageVersion.VERSION);
        // From JavaTimeModule
        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        this.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        this.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
        this.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        this.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        this.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        this.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        this.addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        this.addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        this.addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        this.addDeserializer(Year.class, YearDeserializer.INSTANCE);
        this.addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        this.addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        this.addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
        this.addSerializer(Duration.class, DurationSerializer.INSTANCE);
        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        this.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        this.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        this.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        this.addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        this.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        this.addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        this.addSerializer(Period.class, new ToStringSerializer(Period.class));
        this.addSerializer(Year.class, YearSerializer.INSTANCE);
        this.addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);
        this.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        this.addSerializer(ZoneId.class, new ZoneIdSerializer());
        this.addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));
        this.addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);
        this.addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(YearMonth.class, YearMonthKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);

        // OnSecurity

        // LocalDateTime
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // LocalDate
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        // LocalTime
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));

    }

    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
        context.addValueInstantiators(new ValueInstantiators.Base() {
            public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator) {
                JavaType type = beanDesc.getType();
                Class<?> raw = type.getRawClass();
                if (ZoneId.class.isAssignableFrom(raw) && defaultInstantiator instanceof StdValueInstantiator) {
                    StdValueInstantiator inst = (StdValueInstantiator) defaultInstantiator;
                    AnnotatedClass ac;
                    if (raw == ZoneId.class) {
                        ac = beanDesc.getClassInfo();
                    } else {
                        ac = AnnotatedClassResolver.resolve(config, config.constructType(ZoneId.class), config);
                    }

                    if (!inst.canCreateFromString()) {
                        AnnotatedMethod factory = OnSecurityTimeModule.this._findFactory(ac, "of", String.class);
                        if (factory != null) {
                            inst.configureFromStringCreator(factory);
                        }
                    }
                }

                return defaultInstantiator;
            }
        });
    }

    protected AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes) {
        int argCount = argTypes.length;
        Iterator var5 = cls.getFactoryMethods().iterator();

        AnnotatedMethod method;
        do {
            if (!var5.hasNext()) {
                return null;
            }

            method = (AnnotatedMethod) var5.next();
        } while (!name.equals(method.getName()) || method.getParameterCount() != argCount);

        for (int i = 0; i < argCount; ++i) {
            Class<?> argType = method.getParameter(i).getRawType();
            if (!argType.isAssignableFrom(argTypes[i])) {
            }
        }

        return method;
    }
}
