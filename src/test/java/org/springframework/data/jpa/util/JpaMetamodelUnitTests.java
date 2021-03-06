/*
 * Copyright 2018-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jpa.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * Unit tests for {@link JpaMetamodel}.
 *
 * @author Oliver Gierke
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JpaMetamodelUnitTests {

	@Mock Metamodel metamodel;

	@Mock EntityType<?> type;

	@Test
	void skipsEntityTypesWithoutJavaTypeForIdentifierLookup() {

		doReturn(Collections.singleton(type)).when(metamodel).getEntities();

		assertThat(JpaMetamodel.of(metamodel).isSingleIdAttribute(Object.class, "id", Object.class)).isFalse();
	}

	@Test // DATAJPA-1446
	void cacheIsEffectiveUnlessCleared() {

		JpaMetamodel model = JpaMetamodel.of(metamodel);
		assertThat(model).isEqualTo(JpaMetamodel.of(metamodel));

		JpaMetamodel.clear();
		assertThat(model).isNotEqualTo(JpaMetamodel.of(metamodel));
	}
}
