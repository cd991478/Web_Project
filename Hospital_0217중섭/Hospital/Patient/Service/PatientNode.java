package Hospital.Patient.Service;

import Hospital.Patient.Entity.Patient;

class PatientNode {
    Patient data;
    PatientNode left;
    PatientNode right;

    public PatientNode(Patient data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}