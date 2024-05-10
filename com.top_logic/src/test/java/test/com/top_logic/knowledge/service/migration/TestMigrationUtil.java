/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.knowledge.service.migration;

import static com.top_logic.knowledge.service.migration.MigrationUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import test.com.top_logic.TLTestSetup;
import test.com.top_logic.basic.AssertProtocol;
import test.com.top_logic.basic.BasicTestCase;
import test.com.top_logic.basic.config.AbstractTypedConfigurationTestCase;
import test.com.top_logic.basic.module.ServiceTestSetup;

import com.top_logic.basic.CollectionUtil;
import com.top_logic.basic.Protocol;
import com.top_logic.basic.col.Mapping;
import com.top_logic.basic.config.ConfigurationItem;
import com.top_logic.basic.config.TypedConfiguration;
import com.top_logic.basic.reflect.TypeIndex;
import com.top_logic.dsa.DataAccessService;
import com.top_logic.knowledge.service.migration.MigrationConfig;
import com.top_logic.knowledge.service.migration.MigrationUtil;
import com.top_logic.knowledge.service.migration.Version;

/**
 * Test class for {@link MigrationUtil}.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
@SuppressWarnings("javadoc")
public class TestMigrationUtil extends BasicTestCase {

	/**
	 * Dependency graph
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	private static enum TestModule {
		tl {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] {};
			}
		},
		element {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] { tl };
			}
		},
		importer {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] { element, tl };
			}
		},
		reporting {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] { element, tl };
			}
		},
		ewe {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] { reporting, element, tl };
			}
		},
		demo {
			@Override
			TestModule[] getDependencies() {
				return new TestModule[] { ewe, reporting, importer, element, tl };
			}
		},
		;

		abstract TestModule[] getDependencies();

	}

	/**
	 * The latest version for each module of the simulated software product.
	 */
	private Map<TestModule, Version> _latestVersions;

	/**
	 * The latest versions of each module that was current just before the key version was created.
	 */
	private Map<Version, Map<TestModule, Version>> _latestVersionsAtVersionTime;

	/**
	 * All versions created for a simulated software product in the order they were created.
	 */
	private List<Version> _allVersions;

	/**
	 * The migration instruction for each created version in a simulated software product.
	 */
	private Map<Version, MigrationConfig> _migrationForVersion;

	/**
	 * Counter for creating unique version names in a simulated software product.
	 */
	private int _nextVersionNumber;

	private Protocol _log;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reset();
	}

	private void reset() {
		_log = new AssertProtocol(TestMigrationUtil.class.getName());
		_nextVersionNumber = 0;
		_migrationForVersion = new HashMap<>();
		_allVersions = new ArrayList<>();
		_latestVersions = new EnumMap<>(TestModule.class);
		_latestVersionsAtVersionTime = new HashMap<>();
	}

	public void testGetAllVersions() {
		Version tlInitial = createInitalVersion(TestModule.tl);
		@SuppressWarnings("unused")
		Version elementInitial = createInitalVersion(TestModule.element);
		@SuppressWarnings("unused")
		Version importerInitial = createInitalVersion(TestModule.importer);
		@SuppressWarnings("unused")
		Version eweInitial = createInitalVersion(TestModule.ewe);
		@SuppressWarnings("unused")
		Version reportingInitial = createInitalVersion(TestModule.reporting);
		Version demoInitial = createInitalVersion(TestModule.demo);

		Version tl1 = createVersion(TestModule.tl);
		Version tl2 = createVersion(TestModule.tl);
		Version demo1 = createVersion(TestModule.demo);
		Version tl3 = createVersion(TestModule.tl);
		Version ewe1 = createVersion(TestModule.ewe);
		Version demo2 = createVersion(TestModule.demo);
		Version tl4 = createVersion(TestModule.tl);
		{
			List<MigrationConfig> migrationByVersion = new ArrayList<>();
			migrationByVersion.add(_migrationForVersion.get(demo2));
			migrationByVersion.add(_migrationForVersion.get(demo1));
			List<Version> allVersions = MigrationUtil.getAllVersions(_log, migrationByVersion);
			assertConfigEquals(allVersions, demoInitial, demo1, demo2);
		}
		{
			List<MigrationConfig> migrationByVersion = new ArrayList<>();
			migrationByVersion.add(_migrationForVersion.get(tl4));
			migrationByVersion.add(_migrationForVersion.get(tl3));
			migrationByVersion.add(_migrationForVersion.get(tl2));
			List<Version> allButFirstVersions = MigrationUtil.getAllVersions(_log, migrationByVersion);
			assertConfigEquals(allButFirstVersions, tl1, tl2, tl3, tl4);
			migrationByVersion.add(_migrationForVersion.get(tl1));
			List<Version> allVersions = MigrationUtil.getAllVersions(_log, migrationByVersion);
			assertConfigEquals(allVersions, tlInitial, tl1, tl2, tl3, tl4);
		}
		{
			List<MigrationConfig> migrationByVersion = new ArrayList<>();
			List<Version> allVersions = MigrationUtil.getAllVersions(_log, migrationByVersion);
			assertConfigEquals(allVersions, new Version[0]);
		}
		{
			List<MigrationConfig> migrationByVersion = new ArrayList<>();
			migrationByVersion.add(_migrationForVersion.get(ewe1));
			List<Version> allVersions = MigrationUtil.getAllVersions(_log, migrationByVersion);
			assertConfigEquals(allVersions, ewe1);
		}
	}

	public void testLostDependency() {
		createInitalVersion(TestModule.tl);
		createInitalVersion(TestModule.element);
		createInitalVersion(TestModule.importer);
		createInitalVersion(TestModule.ewe);
		createInitalVersion(TestModule.reporting);
		createInitalVersion(TestModule.demo);

		createVersion(TestModule.tl);
		Version demo1 = createVersion(TestModule.demo);
		createVersion(TestModule.tl);
		createVersion(TestModule.element);
		Version demo2 = createVersion(TestModule.demo);

		// simulate missing module.
		_migrationForVersion.get(demo2).getDependencies().remove(TestModule.tl.name());

		List<MigrationConfig> migrationByVersion = new ArrayList<>();
		migrationByVersion.add(_migrationForVersion.get(demo1));
		migrationByVersion.add(_migrationForVersion.get(demo2));
		try {
			MigrationUtil.getAllVersions(_log, migrationByVersion);
			fail(
				"Migration " + _migrationForVersion.get(demo2) + " is not complete: Base module " + TestModule.tl.name()
				+ " missing.");
		} catch (AssertionFailedError expected) {
			if (!expected.getMessage().contains("Lost module dependency: Migration for")) {
				// Unexpected message
				throw expected;
			}
		}
	}

	public void testCorrectMigrationOrder() {
		createInitalVersion(TestModule.tl);
		createInitalVersion(TestModule.element);
		createInitalVersion(TestModule.importer);
		createInitalVersion(TestModule.ewe);
		createInitalVersion(TestModule.reporting);
		createInitalVersion(TestModule.demo);

		List<Version> versions = new ArrayList<>();
		versions.add(createVersion(TestModule.tl));
		versions.add(createVersion(TestModule.tl));
		versions.add(createVersion(TestModule.element));
		versions.add(createVersion(TestModule.demo));
		versions.add(createVersion(TestModule.ewe));
		versions.add(createVersion(TestModule.element));
		versions.add(createVersion(TestModule.importer));
		checkMigrationOrder(versions);
	}

	/**
	 * Test upgrading a system from a state before introducing automatic migration.
	 */
	public void testFullUpgrade() {
		List<Version> versions = new ArrayList<>();
		versions.add(createVersion(TestModule.tl));
		versions.add(createVersion(TestModule.tl));
		versions.add(createVersion(TestModule.element));
		versions.add(createVersion(TestModule.demo));
		versions.add(createVersion(TestModule.ewe));
		versions.add(createVersion(TestModule.element));
		versions.add(createVersion(TestModule.importer));

		// The version information stored in the database of the simulated software product at
		// the time the migration starts.
		Map<String, Version> currentVersions = Collections.emptyMap();

		assertConfigEquals("Wrong migrations during full upgrade.", expectedMigrations(versions),
			migrationsPerformed(currentVersions));
	}

	@SuppressWarnings("unused")
	public void testCorrectMigrationOrder2() {
		createInitalVersion(TestModule.tl);
		createInitalVersion(TestModule.element);
		createInitalVersion(TestModule.importer);
		createInitalVersion(TestModule.ewe);
		createInitalVersion(TestModule.reporting);
		createInitalVersion(TestModule.demo);

		List<Version> versions = new ArrayList<>();
		versions.add(createVersion(TestModule.reporting));
		if (true) {
			// The order of ewe and importer is not fixed, because the modules are independent. When
			// implementation changes, it may be that the other order may be correct :-(
			versions.add(createVersion(TestModule.ewe));
			versions.add(createVersion(TestModule.importer));
		} else {
			versions.add(createVersion(TestModule.importer));
			versions.add(createVersion(TestModule.ewe));
		}
		versions.add(createVersion(TestModule.demo));
		versions.add(createVersion(TestModule.tl));
		checkMigrationOrder(versions);
	}

	private void checkMigrationOrder(List<Version> versions) {
		for (int skip = 0; skip < versions.size(); skip++) {
			// Simulate that i versions have been dropped from the software product (are no longer
			// part of the delivered artifacts).
			List<Version> suffixList = versions.subList(skip, versions.size());

			// The version information stored in the database of the simulated software product at
			// the time the migration starts.
			Map<String, Version> currentVersions = currentVersions(_latestVersionsAtVersionTime.get(suffixList.get(0)));

			assertConfigEquals("Wrong migrations when skipping " + skip + " versions.", expectedMigrations(suffixList),
				migrationsPerformed(currentVersions));
		}
	}

	private List<MigrationConfig> migrationsPerformed(Map<String, Version> currentVersions) {
		Map<String, Map<Version, MigrationConfig>> availableMigrations = availableMigrations();
		Map<String, List<Version>> versionByModule = getVersionsByModule(_log, availableMigrations);
		return getRelevantMigrations(availableMigrations, versionByModule, currentVersions, moduleDependencies());
	}

	/**
	 * The list of migrations that are expected to be performed for upgrading the given versions.
	 */
	List<MigrationConfig> expectedMigrations(List<Version> versionsToUpgrade) {
		List<MigrationConfig> migrations = new ArrayList<>();
		for (Version v : versionsToUpgrade) {
			migrations.add(_migrationForVersion.get(v));
		}
		return migrations;
	}


	private List<String> moduleDependencies() {
		List<TestModule> c = CollectionUtil.topsort(new Mapping<TestModule, Iterable<? extends TestModule>>() {

			@Override
			public Iterable<? extends TestModule> map(TestModule input) {
				return Arrays.asList(input.getDependencies());
			}
		}, Arrays.asList(TestModule.values()), false);
		List<String> result = new ArrayList<>(c.size());
		for (TestModule module : c) {
			result.add(module.name());
		}
		return result;
	}

	/**
	 * All migration scripts read from the simulated software product indexed by module.
	 */
	private Map<String, Map<Version, MigrationConfig>> availableMigrations() {
		Map<String, Map<Version, MigrationConfig>> result = new HashMap<>();

		// The version information read from the modules of the simulated software product in a
		// stable but otherwise arbitrary order.
		List<Version> discoveredVersions = new ArrayList<>(_migrationForVersion.keySet());
		Collections.shuffle(discoveredVersions, new Random(47));

		for (Version version : discoveredVersions) {
			result
				.computeIfAbsent(version.getModule(), x -> new LinkedHashMap<>())
				.put(version, _migrationForVersion.get(version));
		}
		return result;
	}

	private static void assertConfigEquals(List<? extends ConfigurationItem> actual, ConfigurationItem... expected) {
		assertEquals("Unexpected number of versions.", expected.length, actual.size());
		for (int i = 0; i < actual.size(); i++) {
			AbstractTypedConfigurationTestCase.assertEquals(expected[i], actual.get(i));
		}
	}

	private static Map<String, Version> currentVersions(Map<TestModule, Version> latestVersions) {
		HashMap<String, Version> result = new HashMap<>();
		for (Entry<TestModule, Version> entry : latestVersions.entrySet()) {
			result.put(entry.getKey().name(), entry.getValue());
		}
		return result;
	}

	private static MigrationConfig newMigration(Version version, Iterable<? extends Version> dependencies) {
		MigrationConfig migration = TypedConfiguration.newConfigItem(MigrationConfig.class);
		migration.setVersion(version);
		for (Version dependency : dependencies) {
			migration.getDependencies().put(dependency.getModule(), dependency);
		}
		return migration;
	}

	/**
	 * Simulates creating the implicit initial version for the given module.
	 */
	private Version createInitalVersion(TestModule m) {
		Version initial = newVersion(m.name(), "<initial>");
		_latestVersions.put(m, initial);
		_migrationForVersion.put(initial, newMigration(initial, Collections.emptyList()));
		return initial;
	}

	/**
	 * Simulates creating a new version in the given module.
	 */
	private Version createVersion(TestModule module) {
		Version newVersion = MigrationUtil.newVersion(module.name(), Integer.toString(_nextVersionNumber++));
		MigrationConfig migrationForVersion = newMigration(newVersion, dependentVersions(module));
		_latestVersionsAtVersionTime.put(newVersion, new EnumMap<>(_latestVersions));
		_latestVersions.put(module, newVersion);
		_migrationForVersion.put(newVersion, migrationForVersion);
		_allVersions.add(newVersion);
		return newVersion;
	}

	/**
	 * All versions, an newly created version in the given module would depend on.
	 */
	private Collection<Version> dependentVersions(TestModule module) {
		List<Version> result = new ArrayList<>();
		addLatestVersion(result, module);
		for (TestModule dependencyModule : module.getDependencies()) {
			addLatestVersion(result, dependencyModule);
		}
		return result;
	}

	private void addLatestVersion(List<Version> versions, TestModule module) {
		Version latestVersion = _latestVersions.get(module);
		if (latestVersion != null) {
			versions.add(latestVersion);
		}
	}

	/**
	 * a cumulative {@link Test} for all Tests in {@link TestMigrationUtil}.
	 */
	public static Test suite() {
		Test test = ServiceTestSetup.createSetup(null, TestMigrationUtil.class, TypeIndex.Module.INSTANCE,
			DataAccessService.Module.INSTANCE);
		return TLTestSetup.createTLTestSetup(test);
	}

}

