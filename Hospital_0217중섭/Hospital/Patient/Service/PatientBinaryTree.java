package Hospital.Patient.Service;

import Hospital.Patient.Entity.Patient;

class PatientBinaryTree {
    private PatientNode root;

    // 환자 삽입
    public void insert(Patient data) {
        root = insertRec(root, data);
    }

    private PatientNode insertRec(PatientNode root, Patient data) {
        if (root == null) {
            return new PatientNode(data);
        }
        if (data.getP_UserId().compareTo(root.data.getP_UserId()) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.getP_UserId().compareTo(root.data.getP_UserId()) > 0) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    // 환자 검색 (P_UserId 기준)
    public Patient search(String P_UserId) {
        return searchRec(root, P_UserId);
    }

    private Patient searchRec(PatientNode root, String P_UserId) {
        if (root == null || root.data.getP_UserId().equals(P_UserId)) {
            return root != null ? root.data : null;
        }
        if (P_UserId.compareTo(root.data.getP_UserId()) < 0) {
            return searchRec(root.left, P_UserId);
        }
        return searchRec(root.right, P_UserId);
    }
}