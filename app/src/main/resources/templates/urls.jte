@import hexlet.code.dto.UrlsPage
@param UrlsPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Список URL</title>
</head>
<body>
<div class="container">
    <h1>Список добавленных URL</h1>
        @if(page.getUrl().isEmpty())
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <p>Пока не добавлено ни одного URL</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <ul>
            @for(var url : page.getUrl())
                <li>
                    <a href="/urls/${url.getId()}">${url.getName()}</a>
                </li>
            @endfor
        </ul>

    <a href="/">На главную</a>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</div>>
</body>
</html>