@import gg.jte.Content
@import hexlet.code.dto.BasePage

@param Content content
@param BasePage page = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Website</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <!-- Контент страницы будет вставляться сюда -->

        @if(page != null && page.getFlash() != null)
            <p>${page.getFlash()}</p>
        @endif
        ${content}

    <!-- Футер -->
    <footer class="bg-dark text-white text-center p-3 mt-5">
        <p>Создано для учебных целей.</p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>