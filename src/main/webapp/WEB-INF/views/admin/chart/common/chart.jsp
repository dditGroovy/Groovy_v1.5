<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .chart-inner {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-template-rows: repeat(2, 1fr);
        gap: 24px;
        /*height: 100vh;*/
    }
    .side-header-wrap {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border: 1px solid var(--color-stroke);
        border-radius: var(--vw-12);
        background: var(--color-white);
        padding: 8px var(--vw-24);
        margin-bottom: var(--vh-12);
        margin-bottom: var(--vh-16);
    }

    .card {
        padding: var(--vh-16);
    }
    .header h1 {
        margin-bottom: var(--vh-12);
    }

</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<div class="content-container">
    <header id="tab-header">
        <div class="header">
            <h1><a href="${pageContext.request.contextPath}/common/chart" class="on">통계</a></h1>
            <p class="font-reg font-18">올해 그루비의 통계 자료입니다 📊</p>
        </div>
    </header>
    <main>
        <div class="main-inner chart-inner">
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">👥올해 입퇴사자 현황 (단위: 명)</h1>
                </div>
                <div class="card card-df chart-card">
                    <canvas id="joinChart"></canvas>
                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">💳팀별 법인카드 사용량 (단위: 원)</h1>
                </div>
                <div class="card card-df chart-card">
                    <canvas id="cardChart"></canvas>
                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">💸올해 팀별 연봉 추이 (단위: 원)</h1>
                </div>
                <div class="card card-df chart-card">
                    <canvas id="mbtiChart"></canvas>
                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">🚪전년 대비 회의실 사용 시간 (단위: 시간)</h1>
                </div>
                <div class="card card-df chart-card">
                    <canvas id="roomChart"></canvas>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const join = document.querySelector('#joinChart');

        //만들위치, 설정값객체
        new Chart(join, {
            type: 'line',  // bar, line, pie, doughnut, radar 등등...
            data: {
                labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                datasets: [
                    {
                        label: '올해 입사자',
                        data: [5, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 0],
                        borderWidth: 1,
                    },
                    {
                        label: '올해 퇴사자',
                        data: [0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0],
                        borderWidth: 1,
                    }
                ]
            },
            options: {
                scales: {
                    y: {
                        ticks: {
                            stepSize: 1,
                            min: 0
                        }
                    }
                }
            }
        });
    })
    const roomCtx = document.querySelector('#roomChart');

    //만들위치, 설정값객체
    new Chart(roomCtx, {
        type: 'bar',  // bar, line, pie, doughnut, radar 등등...
        data: {
            labels: ['A101', 'A102', 'A103', 'B101', 'B102', 'B103', 'R001', 'R002', 'R003', 'R004', 'R010', 'R011', 'R012', 'R013'],
            datasets: [
                {
                    label: '작년 대비 회의실별 사용 현황 (단위: 시간)',
                    data: [432, 543, 333, 923, 732, 323, 234, 742, 823, 656, 845, 1034, 745, 398],
                    borderWidth: 1,
                },
                {
                    label: '작년 대비 회의실별 사용 현황 (단위: 시간)',
                    data: [598, 478, 565, 1034, 823, 512, 111, 734, 923, 1023, 854, 1132, 811, 556],
                    borderWidth: 1,
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    const card = document.querySelector('#cardChart');

    new Chart(card, {
        type: 'bar',  // bar, line, pie, doughnut, radar 등등...
        data: {
            labels: ['영업', '인사', '총무', '홍보', '회계'],
            datasets: [
                {
                    label: '올해 팀별 법카 사용액',
                    data: [8503031, 5503810, 6004900, 9423000, 3941002],
                    borderWidth: 1,
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    const mbti = document.querySelector('#mbtiChart');

    new Chart(mbti, {
        type: 'bar',  // bar, line, pie, doughnut, radar 등등...
        data: {
            labels: ['영업', '인사', '총무', '홍보', '회계'],
            datasets: [
                {
                    label: '올해 팀별 평균 급여 추이',
                    data: [4001130, 3153590, 3293590, 3569340, 3881040],
                    borderWidth: 1,
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

</script>