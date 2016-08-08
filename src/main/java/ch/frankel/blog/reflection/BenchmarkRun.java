package ch.frankel.blog.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Date;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

@State(Scope.Benchmark)
public class BenchmarkRun {
	private FieldAccess fieldAccess;
	private Field birthDate;
	private Field birthDate_Mutable;
	private Field firstName;
	private Field firstName_Mutable;
	private Field lastName;
	private Field lastName_Mutable;
	private MethodAccess methodAccess;
	private MethodHandle getBirthDate_Handle;
	private MethodHandle getBirthDate_Handle_UnreflectGetter;
	private MethodHandle getFirstName_Handle;
	private MethodHandle getLastName_Handle;
	private MethodHandle getLastName_Handle_UnreflectGetter;
	private int birthDate_Field;
	private int birthDate_Method;
	private int firstName_Field;
	private int firstName_Method;
	private int lastName_Field;
	private int lastName_Method;
	private MethodHandle getFirstName_Handle_UnreflectGetter;

	public BenchmarkRun() {
		try {
			Class<? extends Person> clazz = ImmutablePerson.class;
			firstName = clazz.getDeclaredField("firstName");
			lastName = clazz.getDeclaredField("lastName");
			birthDate = clazz.getDeclaredField("birthDate");
			Field.setAccessible(new AccessibleObject[] { firstName, lastName, birthDate }, true);

			Class<? extends Person> clazzz = MutablePerson.class;
			firstName_Mutable = clazzz.getDeclaredField("firstName");
			lastName_Mutable = clazzz.getDeclaredField("lastName");
			birthDate_Mutable = clazzz.getDeclaredField("birthDate");
			Field.setAccessible(new AccessibleObject[] { firstName_Mutable, lastName_Mutable, birthDate_Mutable },
					true);

			fieldAccess = FieldAccess.get(MutablePerson.class);
			firstName_Field = fieldAccess.getIndex("firstName");
			lastName_Field = fieldAccess.getIndex("lastName");
			birthDate_Field = fieldAccess.getIndex("birthDate");

			methodAccess = MethodAccess.get(MutablePerson.class);
			firstName_Method = methodAccess.getIndex("getFirstName");
			lastName_Method = methodAccess.getIndex("getLastName");
			birthDate_Method = methodAccess.getIndex("getBirthDate");

			MethodHandles.Lookup lookup = MethodHandles.lookup();
			getFirstName_Handle = lookup.findGetter(MutablePerson.class, "firstName", String.class);
			getLastName_Handle = lookup.findGetter(MutablePerson.class, "lastName", String.class);
			getBirthDate_Handle = lookup.findGetter(MutablePerson.class, "birthDate", Date.class);

			getFirstName_Handle_UnreflectGetter = lookup.unreflectGetter(firstName_Mutable);
			getLastName_Handle_UnreflectGetter = lookup.unreflectGetter(lastName_Mutable);
			getBirthDate_Handle_UnreflectGetter = lookup.unreflectGetter(birthDate_Mutable);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Benchmark
	public void Immutable_Without_Reflection() {
		_No_Reflection(newImmutablePerson());
	}

	@Benchmark
	public void Mutable_Without_Reflection() {
		_No_Reflection(newMutablePerson());
	}

	@Benchmark
	public void Immutable_With_Reflection() throws Exception {
		_Reflection(newImmutablePerson());
	}

	@Benchmark
	public void Mutable_With_Reflection() throws Exception {
		_Reflection(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_ReflectASM_FieldAccess() throws Exception {
		_ReflectASM_FieldAcces(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_ReflectASM_MethodAccess() throws Exception {
		_ReflectASM_MethodAccess(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_JDK_MethodHandles() throws Throwable {
		_MethodHandles(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_JDK_MethodHandles_UnreflectField() throws Throwable {
		_MethodHandles_UnreflectField(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_JDK_MethodHandles_invokeExact() throws Throwable {
		_MethodHandles_invokeExact(newMutablePerson());
	}

	@Benchmark
	public void Mutable_With_JDK_MethodHandles_UnreflectField_invokeExact() throws Throwable {
		_MethodHandles_UnreflectField_invokeExact(newMutablePerson());
	}

	private ImmutablePerson newImmutablePerson() {
		return new ImmutablePerson("John", "Doe", new Date());
	}

	private MutablePerson newMutablePerson() {
		MutablePerson person = new MutablePerson();
		person.setFirstName("John");
		person.setLastName("Doe");
		// very important to not make this a constant - it makes the run 40x faster for direct calls
		person.setBirthDate(new Date());
		return person;
	}

	private void _No_Reflection(Person person) {
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		Date birthDate = person.getBirthDate();
		if (firstName == null || lastName == null || birthDate == null) {
			throw new RuntimeException();
		}
	}

	private void _Reflection(ImmutablePerson person) throws Exception {
		Object o = firstName.get(person);
		Object o1 = lastName.get(person);
		Object o2 = birthDate.get(person);

		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _Reflection(MutablePerson person) throws Exception {
		Object o = firstName_Mutable.get(person);
		Object o1 = lastName_Mutable.get(person);
		Object o2 = birthDate_Mutable.get(person);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _ReflectASM_FieldAcces(MutablePerson person) throws Exception {
		Object o = fieldAccess.get(person, firstName_Field);
		Object o1 = fieldAccess.get(person, lastName_Field);
		Object o2 = fieldAccess.get(person, birthDate_Field);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _ReflectASM_MethodAccess(MutablePerson person) {
		Object o = methodAccess.invoke(person, firstName_Method);
		Object o1 = methodAccess.invoke(person, lastName_Method);
		Object o2 = methodAccess.invoke(person, birthDate_Method);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}

	}

	private void _MethodHandles(MutablePerson person) throws Throwable {
		Object o = getFirstName_Handle.invoke(person);
		Object o1 = getLastName_Handle.invoke(person);
		Object o2 = getBirthDate_Handle.invoke(person);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _MethodHandles_UnreflectField(MutablePerson person) throws Throwable {
		Object o = getFirstName_Handle_UnreflectGetter.invoke(person);
		Object o1 = getLastName_Handle_UnreflectGetter.invoke(person);
		Object o2 = getBirthDate_Handle_UnreflectGetter.invoke(person);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _MethodHandles_invokeExact(MutablePerson person) throws Throwable {
		String o = (String) getFirstName_Handle.invokeExact(person);
		String o1 = (String) getLastName_Handle.invokeExact(person);
		Date o2 = (Date) getBirthDate_Handle.invokeExact(person);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}

	private void _MethodHandles_UnreflectField_invokeExact(MutablePerson person) throws Throwable {
		String o = (String) getFirstName_Handle_UnreflectGetter.invokeExact(person);
		String o1 = (String) getLastName_Handle_UnreflectGetter.invokeExact(person);
		Date o2 = (Date) getBirthDate_Handle_UnreflectGetter.invokeExact(person);
		if (o == null || o1 == null || o2 == null) {
			throw new RuntimeException();
		}
	}
}
