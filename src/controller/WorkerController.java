package controller;
import java.util.ArrayList;
import java.util.List;
import view.SalaryStatus;
import model.WorkerModel;
public class WorkerController {
    public List<WorkerModel> workers;

    public WorkerController() {
        workers = new ArrayList<>();
    }

    public boolean addWorker(WorkerModel worker) {
        if (worker.getId() == null || worker.getId().isEmpty() || isDuplicateId(worker.getId())) {
            return false;
        }
        if (worker.getAge() < 18 || worker.getAge() > 50) {
            return false;
        }
        if (worker.getSalary() <= 0) {
            return false;
        }
        workers.add(worker);
        return true;
    }

    public boolean changeSalary(SalaryStatus status, String code, double amount) {
        WorkerModel worker = getWorkerByCode(code);

        if (worker == null) {
            return false;
        }
        if (status == SalaryStatus.INCREASE) {
            worker.setSalary(worker.getSalary() + amount);
            worker.setStatus("UP");
        } else if (status == SalaryStatus.DECREASE) {
            if (amount <= 0) {
                return false;
            }

            if (worker.getSalary() - amount < 0) {
                return false;
            }

            worker.setSalary(worker.getSalary() - amount);
            worker.setStatus("DOWN");
        }
        return true;
    }

    public List<WorkerModel> getInformationSalary() {
        return workers;
    }

    public boolean isDuplicateId(String id) {
        for (WorkerModel worker : workers) {
            if (worker.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private WorkerModel getWorkerByCode(String code) {
        for (WorkerModel worker : workers) {
            if (worker.getId().equals(code)) {
                return worker;
            }
        }
        return null;
    }
}
