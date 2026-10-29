# JSystems design guidelines

Extracted from [jsystems.pl](https://jsystems.pl/) on 29 October 2026 using a **visible Chrome window** and Playwright CLI. Desktop styles were inspected at 1440 × 1000; responsive behavior was sampled at 390 × 844. This is an observed website reference, not an official brand manual.

## Assets

Paths below are relative to this document. Keep the logo and font files in their original formats.

| Asset | Local file | Notes |
| --- | --- | --- |
| Design tokens | [design-tokens.json](../assets/design-tokens.json) | Colors, typography, spacing, components, responsive rules and provenance |
| Homepage screenshot | [homepage.png](../assets/homepage.png) | Full page, desktop, visually checked without consent or modal overlays |
| JSystems wordmark | [logo.svg](../assets/logo.svg) | Original `logo-colored.svg`; intrinsic size 230 × 72 |
| Favicon | [favicon.png](../assets/favicon.png) | Original PNG, not an ICO conversion |
| Font Awesome 6 Brands, 400 | [fa-brands-400.woff2](../assets/fonts/font-awesome-6-brands/fa-brands-400.woff2) | Brand icons |
| Font Awesome 6 Free, 400 | [fa-regular-400.woff2](../assets/fonts/font-awesome-6-free/fa-regular-400.woff2) | Regular icons |
| Font Awesome 6 Free, 900 | [fa-solid-900.woff2](../assets/fonts/font-awesome-6-free/fa-solid-900.woff2) | Solid interface icons |
| FontAwesome compatibility | [fa-v4compatibility.woff2](../assets/fonts/fontawesome/fa-v4compatibility.woff2) | Legacy icon code points; not a body font |

Verify the usage and redistribution rights for the JSystems logo, favicon and all downloaded fonts before using them.

## Colors

The core brand color is CSS `maroon`, **#800000**. Technology artwork introduces many additional colors; do not treat every image or carousel color as a brand token.

| Token | Value | Observed use |
| --- | --- | --- |
| `colors.brand.primary` | `#800000` | Main actions, contact panel, emphasized headings, navigation hover |
| `colors.brand.accent` | `#EDC82B` | Rating stars and small highlights; secondary accent |
| `colors.brand.info` | `#006EE9` | Supporting links and secondary actions |
| `colors.brand.success` | `#16A34A` | Green “Zapisz się” enrollment buttons |
| `colors.status.guaranteed` | `#2E7D32` | Guaranteed status text |
| `colors.status.guaranteedBackground` | `#E8F5E9` | Pale green status background |
| `colors.status.planned` | `#F57F17` | Planned-date status text |
| `colors.status.plannedBackground` | `#FFF8E1` | Pale amber status background |
| `colors.background.default` | `#FFFFFF` | Main page and light cards |
| `colors.background.light` | `#DEDEDE` | Testimonials section |
| `colors.background.dark` | `#0B0F1A` | Schedule and popular-course sections |
| `colors.background.hero` | `#0D1117` | Hero base beneath technology imagery |
| `colors.background.form` | `#656263` | Contact-form section |
| `colors.text.primary` | `#000000` | Body text and navigation |
| `colors.text.heading` | `#1A1A1A` | Benefit headings |
| `colors.text.secondary` | `#333333` | Supporting text and search icon |
| `colors.text.muted` | `#888888` | Secondary metadata |
| `colors.text.onDark` | `#FFFFFF` | Text and icons on dark or burgundy surfaces |
| `colors.text.headingOnDark` | `#F0F0F0` | Course-section headings |
| `colors.border.light` | `#E6E6E6` | Light control borders |

Dark cards use white at 4% opacity; schedule rows use white at 6%, with 8–10% white borders. Muted dark-surface text uses white at 60%. The scrolled header uses white at 95% and `blur(20px)`; the promotion bar uses burgundy at 92% and `blur(10px)`. `background.overlay` is the observed `rgba(0,0,0,.45)` darkening treatment, not a verified modal specification. No visible form error state was exercised, so `colors.brand.error` is `null`.

## Typography

Use `Poppins, "Poppins Fallback", sans-serif` for the primary interface. The site requests Poppins from [Google Fonts](https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;0,700;0,800;1,400;1,700&display=swap), with normal weights 400, 500, 700 and 800, and italic weights 400 and 700. **Poppins was not downloaded** because it is served by a third party. Computed weight 600 occurs on calls to action even though that weight is not explicitly requested by this stylesheet; browsers can synthesize it.

The local `Poppins Fallback` faces use `local(Arial)` / Arial Bold / italic equivalents and Liberation Sans alternatives. They are metric-adjusted fallbacks, not separate downloadable brand fonts. The normal regular fallback uses `size-adjust:108.6%`, `ascent-override:96.68%`, `descent-override:32.23%`, and `line-gap-override:9.21%`; weight ranges have different adjustments. `Inter Fallback` is also declared but was not the observed homepage body font.

Some native controls use **Arial**: the hero search field, its “Szukaj” button and enrollment buttons. Footer text fields explicitly use Poppins. Preserve these distinctions when matching the captured page exactly.

| Role | Size | Weight | Line height |
| --- | --- | --- | --- |
| Metadata / small badges | 11–14px | 400–700 | Component dependent |
| Body | 16px | 400 | `normal`, 1.4 or 1.6 by component |
| Main navigation | 18px | 700 | `normal` |
| Popular-course card title | 16px | 700 | 1.35 |
| Schedule row title | 18px | 700 | 1.25 |
| Benefit card title | 20px | 700 | 1.3 |
| Carousel course title | 22px | 800 | 1.3 |
| Popular-course section heading | 26px | 800 | `normal` |
| Schedule section heading | 32px | 800 | `normal` |
| Footer heading | 34px | 800 | 1.2 |
| Benefits section heading | 36px | 700 | 1.2 |
| About heading | 38px | 800 | 1.15 |

The source defines a 42px/700 hero h1, but the loaded site clears its text and sets opacity to zero. Treat `h1SourceOnly` as a CSS reference, not visible content to reproduce. Supporting size tokens include 15, 17, 24 and 34px. The observed weight scale is 400, 500, 600, 700 and 800; 900 is used for solid icons.

### Local icon fonts

Each downloaded font has a usable repository path:

- `assets/fonts/font-awesome-6-brands/fa-brands-400.woff2`
- `assets/fonts/font-awesome-6-free/fa-regular-400.woff2`
- `assets/fonts/font-awesome-6-free/fa-solid-900.woff2`
- `assets/fonts/fontawesome/fa-v4compatibility.woff2`

These came from the site's self-hosted Font Awesome CSS. Verify their licenses before use. Example declarations below assume a stylesheet in a directory beside `assets`; adjust URLs to your app's asset serving setup.

```css
@font-face {
  font-family: "Font Awesome 6 Brands";
  src: url("../assets/fonts/font-awesome-6-brands/fa-brands-400.woff2") format("woff2");
  font-style: normal;
  font-weight: 400;
  font-display: block;
}
@font-face {
  font-family: "Font Awesome 6 Free";
  src: url("../assets/fonts/font-awesome-6-free/fa-regular-400.woff2") format("woff2");
  font-style: normal;
  font-weight: 400;
  font-display: block;
}
@font-face {
  font-family: "Font Awesome 6 Free";
  src: url("../assets/fonts/font-awesome-6-free/fa-solid-900.woff2") format("woff2");
  font-style: normal;
  font-weight: 900;
  font-display: block;
}
```

The v4 compatibility file is a partial legacy code-point mapping. Use it with the appropriate Font Awesome compatibility CSS; it is not a replacement for the regular or solid face. Use accessible labels on icon-only controls.

## Spacing and layout

A **4px base** is a suggested normalization for new work. The convenience token scale is 4, 8, 12, 16, 20, 24, 28, 32, 40, 48, 60 and 80px; the site does not expose one consistent global spacing scale. Frequently observed exceptions include 2, 3, 5, 6, 9, 10, 14, 18, 22, 25, 26, 45 and 50px. Match individual components using their measured values.

Desktop header padding is `20px 50px`; navigation gap is 30px. Popular courses use an 18px grid gap. The footer form has a 1200px maximum width, a 100px desktop column gap and 40px row separation. Large contact sections use 90px vertical padding; testimonials use `130px 0 50px`. A few rendered dimensions differ from declared CSS pixels because the source applies scaling, so prefer the declared padding, radius and typography tokens over screenshot pixel measurements.

## Border radius

| Value | Usage / scope |
| --- | --- |
| 0 | Rectangular search-button segment, header and full-width bands |
| 4 / 6px | Small details in the broader page style survey; use only when matching the reference |
| 8px | Footer fields and schedule action buttons |
| 10px | Tooltip and secondary detail treatments |
| 12px | Schedule rows and popular-course cards |
| 14px | Hero search container |
| 16 / 20px | Rounded secondary panels and carousel surfaces |
| 22 / 24px | Specialized review/panel details in the broader style survey |
| 30px | Hero container at the sampled mobile width |
| 40px | Decorative outer card corner; source includes `40px 0 0 0` |
| 50px | Pill calls to action and desktop hero container |
| 50% | Round icons and carousel controls |
| `0 0 8px 8px` | Bottom-only rounded detail panels |

The source pill value is **50px**, rather than an inferred 999px. Lesser-used radii are reference values, not a rule to introduce all of them into new components.

## Components

### Header, promotion bar and navigation

The promotion bar stays fixed at the top with white messaging, contact details and a translucent burgundy background. The header is fixed beneath it (`top:48px` in CSS). At the top of the page its background is transparent and the wordmark is white; after scrolling it gains a 95% white surface, a 20px blur and a soft shadow, and restores the colored logo. Navigation uses black 18px/700 Poppins text, natural casing and burgundy hover/emphasis. Hover transitions take 0.4 seconds.

### Hero and search

The hero combines a heavily rounded technology background with a burgundy introductory pill, a centered white search form and rotating training artwork. The search container has a 14px radius, a 640px maximum width and shadow `0 10px 40px rgba(0,0,0,.28)`. The field uses 17px Arial with `16px 14px` padding. Its burgundy submit button uses white 16px/700 Arial and `16px 30px` padding; the container supplies the outer rounded edge.

### Buttons

| Variant | Treatment |
| --- | --- |
| Search action | Burgundy fill, white text, `16px 30px`, square inner segment |
| Inverse pill | White fill, burgundy 17px/600 text, `12px 35px`, 50px radius |
| Hero inverse pill | White fill, burgundy 18px/700 text, `14px 40px`, soft shadow |
| Schedule detail | Burgundy fill, white 14px/700 text, `9px 18px`, 8px radius |
| Enrollment | Green `#16A34A`, white 14px/700 text, `9px 18px`, 8px radius |
| Contact outline | Transparent, 1px white outline, white 24px/600 text, `14px 50px`, 50px radius |

The inverse card button has 0.3px letter spacing; the contact outline has 0.5px. Do not assign a single universal button font or radius to every control.

### Schedule and course cards

Dark schedule rows use a 12px radius, `8px 15px` padding, a 10px column gap, translucent white fill and a fine white border. Date/status metadata is compact; actions sit beside the training information on desktop. Popular-course cards combine artwork with dark text panels, a 12px radius and translucent borders. Their source hover state changes the border to burgundy, adds a 14% burgundy wash and translates the card upward by 3px over 0.2 seconds.

### Inputs and footer

The contact section uses burgundy, followed by a graphite form panel with white labels and fields. Footer fields use 16px Poppins, `14px 18px` padding, no border and an 8px radius. Input height is 50px and textarea height is 190px in source CSS. Focus adds `0 0 0 3px rgba(255,255,255,.2)`. Field labels use 20px/500. No submission, error response or success response was triggered during extraction.

### Trust and testimonials

White benefit cards use burgundy circular icons and restrained shadows. Testimonials sit on a light gray band with burgundy headings, white review cards, gold ratings and large directional controls. Company logos form a horizontal trust strip. Preserve generous space around these proof points.

## Responsive behavior

- At 1270px and below, the desktop navigation gap reduces to 15px.
- At 1150px and below, navigation becomes an off-canvas vertical menu; the hamburger opens it. A closed menu still has `display:flex`, so visibility must also consider its transform.
- The popular-course grid has four desktop columns, two at 1100px and below, and one at 600px and below. The smallest layout uses horizontal cards and a 12px gap.
- The schedule heading changes from 32px to 26px at 1050px and 22px at 500px. Popular-course headings use 22px at 600px and below.
- At the sampled 390px width, header padding is 20px, header gap is 10px, the hero radius is 30px, schedule titles are 20px, and the stacked form gap is 20px.

## Logo usage

Use the supplied SVG without stretching or cropping. Preserve its `230:72` aspect ratio and complete wordmark. The header displayed it at approximately 207 × 72 rendered pixels in the sampled desktop environment; use the SVG view box for intrinsic proportions rather than reproducing that measurement as a stretched image.

On light backgrounds, use the original colored logo. The website creates the white version on dark or image backgrounds with `filter:brightness(0) invert(1)`; no separate inverted asset was found or generated. Keep sufficient clear space for legibility; no official minimum size or exclusion-zone rule was available. The source logo is [logo-colored.svg](https://jsystems.pl/static/images/logo-colored.svg).

## Visual style

JSystems presents a professional training brand with strong burgundy accents and rounded, approachable controls. Dark technology imagery and dense course panels give the training catalog an energetic technical character. Poppins headings, bold guarantees and green enrollment actions make key decisions easy to find. White benefit cards, testimonials and company logos provide a calmer rhythm between promotional areas.

## Capture limitations

The carousel changes over time, so a later visit may show different artwork or dates. The saved screenshot preserves the source page's long layout and whitespace. Consent, tracking and chat scripts reported network failures in this environment; no cookie banner, modal or chat overlay covered the verified capture. Hidden trainer dialogs and inactive carousel content were excluded when choosing component colors and typography. This extraction did not exercise form validation or authenticated pages.
