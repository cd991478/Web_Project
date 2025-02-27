package test;
import org.junit.jupiter.api.Test;

import Hospital.Patient.Entity.Patient;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class PatientBinaryTreeTest {

    @Test
    public void testPerformance() {
        PatientBinaryTree tree = new PatientBinaryTree();
        int dataSize = 10000000; // 100만 개 데이터 테스트
        List<Patient> patients = new ArrayList<>();

        // 100만 개 환자 정보 삽입
        for (int i = 0; i < dataSize; i++) {
            Patient patient = new Patient();
            patient.setP_UserId("USER" + i);
            patients.add(patient);
            tree.insert(patient);
        }
        // 검색 성능 측정
        long startTime = System.nanoTime();
        Patient result = tree.search("USER5000000"); // 중간 값 검색
        long endTime = System.nanoTime();

        double elapsedTime = (endTime - startTime) / 1_000_000.0; // 밀리초 변환
        System.out.println("이진트리 검색 속도: " + elapsedTime + "ms");

        // 검색이 정상적으로 수행되었는지 확인
        assertNotNull(result);
        assertEquals("USER500000", result.getP_UserId());
    }
}