<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .chart-inner {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-template-rows: repeat(2, 1fr);
        height: 100vh;
    }
</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<div class="content-container">
    <header id="tab-header">
        <h1><a href="${pageContext.request.contextPath}/common/chart" class="on">통계</a></h1>
        <div class="sub-title">
            <h2 class="main-desc">올해 그루비의 통계 자료입니다 📊</h2>
        </div>
    </header>
    <main>
        <div class="main-inner chart-inner">
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">👥올해 입퇴사자 현황</h1>
                </div>
                <div class="card card-df chart-card">
                    <div id="joinChart"></div>
                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">급여 기본 설정</h1>
                </div>
                <div class="card card-df chart-card">

                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">급여 기본 설정</h1>
                </div>
                <div class="card card-df chart-card">

                </div>
            </div>
            <div class="item">
                <div class="side-header-wrap">
                    <h1 class="font-md font-18 color-font-md">급여 기본 설정</h1>
                </div>
                <div class="card card-df chart-card">

                </div>
            </div>
        </div>
    </main>
</div>

<script>
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
</script>