package MybatisTest;

public interface StudentMapper{
        public void insertStudent(Student student);
        public void deleteStudent(Student student);
        public void updateStudent(Student student);
        public void selectStudentAll(Student student);
}