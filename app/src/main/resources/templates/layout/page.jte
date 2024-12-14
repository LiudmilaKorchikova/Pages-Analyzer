@import com.fasterxml.jackson.databind.util.Named
@import gg.jte.Content
@import hexlet.code.dto.BasePage
@import hexlet.code.utils.NamedRoutes

@param Content content
@param BasePage page = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pages Analyser</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Анализатор страниц</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="/">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container my-4"> <!-- Контейнер с отступами для контента -->
    <!-- Контент страницы будет вставляться сюда -->

    @if(page != null && page.getFlash() != null)
        <div class="alert alert-info alert-dismissible fade show mb-4" role="alert">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Закрыть"></button>
        </div>
    @endif

    ${content}

</div>

<!-- Футер -->
<footer class="bg-dark text-white text-center p-3 mt-5">
    <p>Создано для учебных целей.</p>
</footer>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>