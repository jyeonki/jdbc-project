package com.jdbc.basic.pay.view;

import com.jdbc.basic.pay.controller.PayController;
import com.jdbc.basic.pay.domain.Emp;
import com.jdbc.basic.pay.domain.Pay;
import com.jdbc.basic.pay.domain.Rank;

import java.util.List;
import java.util.Scanner;

public class PayMenu {

    private final PayController payController;
    private final Scanner sc;

    public PayMenu() {
        payController = new PayController();
        sc = new Scanner(System.in);
    }


    public void mainMenu() {

        while (true) {
            System.out.println("\n============================== 급여 관리 프로그램 ==============================");
            System.out.println("# 1. 사원 정보 입력");
            System.out.println("# 2. 사원 전체 조회");
            System.out.println("# 3. 사원 개별 조회");
            System.out.println("# 4. 사원 정보 수정");
            System.out.println("# 5. 사원 정보 삭제");
            System.out.println("# 6. 직급 정보 추가");
            System.out.println("# 7. 직급 정보 수정");
            System.out.println("# 8. 직급 정보 삭제");
            System.out.println("# 9. 끝내기");

            int menu = inputNum("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    // 사원 정보 입력
                    insertMenu();
                    break;
                case 2:
                    // 사원 전체 조회
                    findAllMenu();
                    break;
                case 3:
                    // 특정 사원 급여 조회
                    findOneMenu();
                    break;
                case 4:
                    // 사원 급여 정보 수정 (직급 수정)
                    modifyMenu();
                    break;
                case 5:
                    // 사원 정보 삭제
                    removeMenu();
                    break;
                case 6:
                    // 직급 정보 추가
                    insertRankMenu();
                    break;
                case 7:
                    // 직급 정보 수정
                    modifyRankMenu();
                    break;
                case 8:
                    // 직급 정보 삭제
                    removeRankMenu();
                    break;
                case 9:
                    System.out.println("\n# 프로그램을 종료합니다.");
                    System.exit(0); // 프로세스 종료
                    return;
                default:
                    System.out.println("\n# 없는 메뉴 번호입니다.\n# 다시 입력하세요.");
            }
        }
    }



    // 1번 메뉴
    private void insertMenu() {

        System.out.println("\n# 사원 정보 입력을 시작합니다.");

        String empName = inputStr("- 이름: ");
        String empRank = inputStr("- 직급 [사원, 대리, 과장, 부장]: ");

        Emp emp = new Emp();
        emp.setEmpName(empName);

        if (empRank.equals("사원") || empRank.equals("대리") || empRank.equals("과장") || empRank.equals("부장"))
        {

        } else
        {
            while (true) {
                System.out.println("\n# [사원, 대리, 과장, 부장]만 입력이 가능합니다.");
                System.out.println("\n# 다시 입력하세요");
                empRank = inputStr("- 직급 [사원, 대리, 과장, 부장]: ");
                if (empRank.equals("사원") || empRank.equals("대리") || empRank.equals("과장") || empRank.equals("부장")) {
                    break;
                }
            }
        }

        int rankId = payController.findOneRankId(empRank);

        emp.setRankId(rankId);

        payController.insertEmp(emp);

        System.out.printf("\n# %s님의 정보가 저장되었습니다.\n", empName);
    }


    // 2번 메뉴
    private void findAllMenu() {

        List<Pay> payList = payController.selectAllWholePay();

        System.out.printf("\n================================ 직원 급여 정보 ================================\n");

        System.out.print("\n|  사원번호  |   이름   |  직급ID  |   직급   |   기본급   |   세율   |   순급여   |\n");
//        System.out.printf("\n  %10s%8s%8s%9s%8s%9s\n", "사원번호", "이름", "직급", "기본급", "세율", "순급여");

        for (Pay pay : payList) {
            System.out.printf("%10s %7s %7d %9s %13d %8.2f %12d\n", pay.getEmpNo(), pay.getEmpName(), pay.getRankId(), pay.getRankName(), pay.getBasePay(), pay.getTaxRate(), pay.getNetSalary());
        }
    }


    // 3번 메뉴
    private void findOneMenu() {

        System.out.println("\n# 조회할 사원의 사원번호를 입력하세요.");
        String empNo = inputStr(">>> ");

        Pay pay = payController.selectOneWholePay(empNo);

        if (pay != null)
        {
            System.out.println("\n================================ 직원 급여 정보 ================================\n");
            System.out.print("|  사원번호  |   이름   |  직급ID  |   직급   |   기본급   |   세율   |   순급여   |\n");
            System.out.printf("%10s %7s %7d %9s %13d %8.2f %12d\n\n", pay.getEmpNo(), pay.getEmpName(), pay.getRankId(), pay.getRankName(), pay.getBasePay(), pay.getTaxRate(), pay.getNetSalary());

        } else
        {
            System.out.println("\n# 해당 사원번호는 존재하지 않습니다.");
        }
    }


    // 4번 메뉴
    private void modifyMenu() {

        System.out.println("\n# 수정할 사원의 사원번호를 입력하세요.");
        String empNo = inputStr(">>> ");
        Emp oldEmp = payController.findOneEmp(empNo);
        Rank oldRank = payController.findOneRank(oldEmp.getRankId());


        if (payController.hasEmp(empNo))
        {
            String oldRankName = oldRank.getRankName();
            System.out.println("\n# 해당 사원의 직급을 수정하세요 [사원, 대리, 과장, 부장]");
            String newRank = inputStr("- 새로운 직급 (현재 직급: " + oldRankName + "): ");

            if (newRank.equals("사원") || newRank.equals("대리") || newRank.equals("과장") || newRank.equals("부장"))
            {
                int newRankId = payController.findOneRankId(newRank);
                boolean flag = payController.updateEmp(empNo, newRankId);
                if (flag)
                {
                    System.out.println("\n# 수정이 완료되었습니다.");

                } else
                {
                    System.out.println("\n# 수정이 실패했습니다.");
                }
            } else
            {
                while (true) {
                    System.out.println("\n# [사원, 대리, 과장, 부장]만 입력이 가능합니다.");
                    System.out.println("\n# 다시 입력하세요");
                    newRank = inputStr("- 새로운 직급 (현재 직급: " + oldRankName + "): ");
                    if (newRank.equals("사원") || newRank.equals("대리") || newRank.equals("과장") || newRank.equals("부장")) {
                        break;
                    }
                }
            }

        } else
        {
            System.out.println("\n# 해당 사원번호는 존재하지 않습니다.");
        }
    }

    // 5번 메뉴
    private void removeMenu() {

        System.out.println("\n# 삭제할 사원의 사원번호를 입력하세요!");
        String empNo = inputStr(">>> ");

        if (payController.hasEmp(empNo)) {

            boolean flag = payController.deleteEmp(empNo);
            if (flag) {
                System.out.println("\n# 삭제가 완료되었습니다.");
            } else {
                System.out.println("\n# 삭제에 실패했습니다.");
            }

        } else {
            System.out.println("\n# 해당 사원번호는 존재하지 않습니다.");
        }
    }

    // 직급 정보 추가 메뉴
    private void insertRankMenu() {

        System.out.println("\n# 직급 정보 입력을 시작합니다.");

        int rankId = inputNum("- 직급ID: ");
        String rankName = inputStr("- 직급명: "); // 추가할 것 - 직급명에 현재 있는 직급들 보여주기
        int basePay = inputNum("- 기본급: ");
        double taxRate = inputDouble(" - 세율: ");

        Rank rank = new Rank();
        rank.setRankId(rankId);
        rank.setRankName(rankName);
        rank.setBasePay(basePay);
        rank.setTaxRate(taxRate);

        payController.insertRank(rank);

        System.out.printf("\n# 직급 '%s'정보가 저장되었습니다.\n", rankName);
    }

    // 직급 정보 수정 메뉴
    private void modifyRankMenu() {

        System.out.println("\n# 수정할 직급의 직급ID를 입력하세요.");
        // 추가할 것 - 직급 목록 보여주기
        findAllMenu();

        int rankID = inputNum("\n>>> ");

        if (payController.hasRank(rankID)) {

            Rank oldRank = payController.findOneRank(rankID);
            Rank newRank = payController.findOneRank(rankID);

            String newRankName = inputStr("- 직급명 (현재 직급: " + oldRank.getRankName() + "): ");
            int newBasePay = inputNum("- 기본급 (현재 기본급: " + oldRank.getBasePay() + "): ");
            double newTaxRate = inputDouble("- 세율 (현재 세율: " + oldRank.getTaxRate() + "): ");

            newRank.setRankName(newRankName);
            newRank.setBasePay(newBasePay);
            newRank.setTaxRate(newTaxRate);

            boolean flag = payController.updateRank(newRank);
                if (flag)
                {
                    System.out.println("\n# 수정이 완료되었습니다.");

                } else
                {
                    System.out.println("\n# 수정이 실패했습니다.");
                }
        } else
        {
            System.out.println("\n# 해당 직급ID는 존재하지 않습니다.");
        }
    }

    private void removeRankMenu() {

        System.out.println("\n# 삭제할 직급의 직급ID를 입력하세요!");
        findAllMenu();

        int rankId = inputNum(">>> ");

        if (payController.hasRank(rankId)) {

            boolean flag = payController.deleteRank(rankId);
            if (flag) {
                System.out.println("\n# 삭제가 완료되었습니다.");
            } else {
                System.out.println("\n# 삭제에 실패했습니다.");
            }

        } else {
            System.out.println("\n# 해당 직급ID는 존재하지 않습니다.");
        }
    }

    // 문자 입력 메서드
    private String inputStr(String msg) {
        System.out.printf(msg);
        return sc.nextLine();
    }

    // 숫자 double 입력 메서드
//    private double inputDouble(String msg) {
//
//        double n;
//        while (true) {
//            try {
//                System.out.print(msg);
//                n = sc.nextDouble();
//                break;
//            } catch (Exception e) {
//                sc.nextLine();
//                System.out.println("# 숫자로만 입력하세요");
//            }
//        }
//        return n;
//    }

    // 숫자 double 입력 메서드
    private double inputDouble(String msg) {

        String value;

        while (true) {
            try {
                System.out.print(msg);
                value = sc.nextLine();

                return Double.parseDouble(value);

            } catch (Exception e) {
//                sc.nextLine();
                e.printStackTrace();
                System.out.println("\n숫자로만 입력하세요.");
            }
        }
    }

    // 숫자 int 입력 메서드
    private int inputNum(String msg) {

        String value;

        while (true) {
            try {
                System.out.print(msg);
                value = sc.nextLine();
                int nValue = Integer.parseInt(value);

                return nValue;
            } catch (NumberFormatException ex) {
//                sc.nextLine();
                System.out.println("\n정수로만 입력하세요.");
            }
        }
    }
}
