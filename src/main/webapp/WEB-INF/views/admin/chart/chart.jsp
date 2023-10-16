<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .chart-inner {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-template-rows: repeat(2, 1fr);
        height: 100vh;
    }
</style>
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