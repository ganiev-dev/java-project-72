@import hexlet.code.dto.UrlPage
@param UrlPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${page.getHeader()}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>${page.getHeader()}</h1>

    <a href="${page.getUrl().getName()}" target="_blank"> ${page.getUrl().getName()}</a>
</div>>
</body>
</html>